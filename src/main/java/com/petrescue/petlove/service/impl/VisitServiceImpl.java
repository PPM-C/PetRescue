package com.petrescue.petlove.service.impl;

import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.dto.VisitDto;
import com.petrescue.petlove.dto.VisitUpdateDto;
import com.petrescue.petlove.enums.VisitStatus;
import com.petrescue.petlove.model.shelter.Visit;
import com.petrescue.petlove.repository.VisitRepository;
import com.petrescue.petlove.service.interfaces.ShelterService;
import com.petrescue.petlove.service.interfaces.VisitService;
import com.petrescue.petlove.service.interfaces.VisitorService;
import com.petrescue.petlove.service.interfaces.AdopterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<VisitDto> list(Long adoptionRequestId, String status, Pageable pageable) {
        Page<Visit> page;
        if (adoptionRequestId != null) {
            page = visitRepository.findByAdoptionRequestId(adoptionRequestId, pageable);
        } else {
            page = visitRepository.findAll(pageable);
        }
        if (status != null && !status.isBlank()) {
            VisitStatus wanted = VisitStatus.valueOf(status.toUpperCase());
            page = page.map(v -> v) // no-op para mantener tipo
                       .filter(v -> v.getStatus() == wanted);
        }
        return page.map(this::toDto);
    }

    @Override
    public VisitDto create(VisitCreateDto dto) {
        if (dto.getRequestId() == null) {
            throw new IllegalArgumentException("requestId is required");
        }
        Visit v = new Visit();
        v.setAdoptionRequestId(dto.getRequestId());
        v.setScheduledAt(dto.getScheduledAt() != null ? dto.getScheduledAt() : LocalDateTime.now().plusDays(1));
        // Usa el valor que tenga tu enum. Si tu enum no tiene SCHEDULED, cambia por el válido (p. ej. PENDING o CONFIRMED).
        v.setStatus(VisitStatus.SCHEDULED);
        v.setNotes(dto.getNotes());

        v = visitRepository.save(v);
        return toDto(v);
    }

    @Override
    public VisitDto update(Long id, VisitUpdateDto dto) {
        Visit v = visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found: " + id));

        if (dto.getScheduledAt() != null) {
            v.setScheduledAt(dto.getScheduledAt());
        }
        if (dto.getStatus() != null) {
            v.setStatus(dto.getStatus());
        }
        if (dto.getNotes() != null) {
            v.setNotes(dto.getNotes());
        }

        v = visitRepository.save(v);
        return toDto(v);
    }

    @Override
    public VisitDto cancel(Long id) {
        Visit v = visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found: " + id));
        // Cambia por el literal correcto si tu enum usa otro nombre
        v.setStatus(VisitStatus.CANCELLED);
        v = visitRepository.save(v);
        return toDto(v);
    }

    @Override
    public void delete(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new EntityNotFoundException("Visit not found: " + id);
        }
        visitRepository.deleteById(id);
    }

    private VisitDto toDto(Visit v) {
        return new VisitDto(
                v.getId(),
                v.getAdoptionRequestId(),
                v.getScheduledAt(),
                v.getStatus(),
                v.getNotes()
        );
    }
}
