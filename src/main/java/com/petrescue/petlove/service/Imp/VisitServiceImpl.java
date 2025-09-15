package com.petrescue.petlove.service.Imp;

import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.dto.VisitDto;
import com.petrescue.petlove.dto.VisitRescheduleDto;
import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.enums.VisitStatus;
import com.petrescue.petlove.model.adoption.AdoptionRequest;
import com.petrescue.petlove.model.shelter.Visit;
import com.petrescue.petlove.repository.AdoptionRequestRepository;
import com.petrescue.petlove.repository.VisitRepository;
import com.petrescue.petlove.service.Interface.VisitService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository repo;
    private final AdoptionRequestRepository requestRepo;

    @Override
    public Page<VisitDto> list(Long requestId, Pageable pageable) {
        if (requestId == null) {
            return repo.findAll(pageable).map(this::toDto);
        }
        var list = repo.findByAdoptionRequest_Id(requestId).stream().map(this::toDto).toList();
        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    @Transactional
    public VisitDto schedule(Long requestId, VisitCreateDto dto) {
        AdoptionRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request " + requestId + " not found"));
        if (req.getStatus() != AdoptionStatus.Pending)
            throw new IllegalArgumentException("Sólo se programan visitas para solicitudes PENDING");
        if (dto.scheduledAt().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("La visita debe ser a futuro");

        Visit v = Visit.builder()
                .adoptionRequest(req)
                .scheduledAt(dto.scheduledAt())
                .status(VisitStatus.Scheduled)
                .notes(dto.notes())
                .build();
        return toDto(repo.save(v));
    }

    @Override
    @Transactional
    public VisitDto reschedule(Long visitId, VisitRescheduleDto dto) {
        Visit v = repo.findById(visitId).orElseThrow(() -> new EntityNotFoundException("Visit " + visitId + " not found"));
        if (v.getStatus() != VisitStatus.Scheduled) throw new IllegalArgumentException("Sólo se reprograman visitas SCHEDULED");
        if (dto.scheduledAt() != null) {
            if (dto.scheduledAt().isBefore(LocalDateTime.now()))
                throw new IllegalArgumentException("La nueva fecha debe ser futura");
            v.setScheduledAt(dto.scheduledAt());
        }
        if (dto.notes() != null) v.setNotes(dto.notes());
        return toDto(v);
    }

    @Override
    @Transactional
    public VisitDto cancel(Long visitId, VisitRescheduleDto reasonMaybe) {
        Visit v = repo.findById(visitId).orElseThrow(() -> new EntityNotFoundException("Visit " + visitId + " not found"));
        v.setStatus(VisitStatus.Canceled);
        if (reasonMaybe != null && reasonMaybe.notes() != null) {
            v.setNotes(append(v.getNotes(), "Cancelada: " + reasonMaybe.notes()));
        }
        return toDto(v);
    }

    @Override
    @Transactional
    public VisitDto complete(Long visitId, VisitRescheduleDto notesMaybe) {
        Visit v = repo.findById(visitId).orElseThrow(() -> new EntityNotFoundException("Visit " + visitId + " not found"));
        v.setStatus(VisitStatus.Completed);
        if (notesMaybe != null && notesMaybe.notes() != null) {
            v.setNotes(append(v.getNotes(), notesMaybe.notes()));
        }
        return toDto(v);
    }

    private String append(String old, String extra) {
        if (extra == null || extra.isBlank()) return old;
        return (old == null || old.isBlank()) ? extra : old + "\n" + extra;
    }

    private VisitDto toDto(Visit v) {
        return new VisitDto(
                v.getId(),
                v.getAdoptionRequest().getId(),
                v.getStatus(),
                v.getScheduledAt(),
                v.getNotes(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );
    }
}