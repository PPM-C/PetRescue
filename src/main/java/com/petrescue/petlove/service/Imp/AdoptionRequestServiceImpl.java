package com.petrescue.petlove.service.Imp;

import com.petrescue.petlove.dto.AdoptionRequestCreateDto;
import com.petrescue.petlove.dto.AdoptionRequestDto;
import com.petrescue.petlove.dto.DecisionDto;
import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.enums.PetStatus;
import com.petrescue.petlove.model.pet.Pet;
import com.petrescue.petlove.model.adoption.AdoptionRequest;
import com.petrescue.petlove.repository.AdopterRepository;
import com.petrescue.petlove.repository.AdoptionRequestRepository;
import com.petrescue.petlove.repository.PetRepository;
import com.petrescue.petlove.service.Interface.AdoptionRequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionRequestServiceImpl implements AdoptionRequestService {

    private final AdoptionRequestRepository repo;
    private final PetRepository petRepo;
    private final AdopterRepository adopterRepo;

    @Override
    public Page<AdoptionRequestDto> list(Long petId, Long adopterId, Pageable pageable) {
        Page<AdoptionRequest> page = repo.findAll(pageable);
        var dtos = page.getContent().stream()
                .filter(ar -> (petId == null || ar.getPet().getId().equals(petId)) &&
                        (adopterId == null || ar.getAdopter().getId().equals(adopterId)))
                .map(this::toDto)
                .toList();

        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public AdoptionRequestDto create(AdoptionRequestCreateDto dto) {
        var adopter = adopterRepo.findById(dto.adopterId())
                .orElseThrow(() -> new EntityNotFoundException("Adopter " + dto.adopterId() + " not found"));
        var pet = petRepo.findById(dto.petId())
                .orElseThrow(() -> new EntityNotFoundException("Pet " + dto.petId() + " not found"));

        if (pet.getStatus() == PetStatus.Adopted)
            throw new IllegalArgumentException("La mascota ya está adoptada");

        boolean dup = repo.existsByAdopter_IdAndPet_IdAndStatusIn(
                adopter.getId(),
                pet.getId(),
                List.of(AdoptionStatus.Pending, AdoptionStatus.Approved)
        );
        if (dup) throw new IllegalArgumentException("Ya existe una solicitud activa para esta mascota");

        AdoptionRequest ar = AdoptionRequest.builder()
                .adopter(adopter)
                .pet(pet)
                .status(AdoptionStatus.Pending)
                .message(dto.message())
                .build();

        return toDto(repo.save(ar));
    }

    @Override
    @Transactional
    public AdoptionRequestDto approve(Long id, DecisionDto reasonMaybe) {
        AdoptionRequest req = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request " + id + " not found"));

        if (req.getStatus() != AdoptionStatus.Pending)
            throw new IllegalArgumentException("La solicitud no está pendiente");

        Pet pet = req.getPet();
        if (pet.getStatus() == PetStatus.Adopted)
            throw new IllegalArgumentException("La mascota ya fue adoptada");

        req.setStatus(AdoptionStatus.Approved);
        req.setDecidedAt(LocalDateTime.now());
        req.setDecisionReason(reasonMaybe != null ? reasonMaybe.reason() : null);

        pet.setStatus(PetStatus.Adopted);
        pet.setDepartureDate(LocalDate.now());

        // auto-rechazar otras Pending del mismo pet
        repo.findByPet_IdAndStatus(pet.getId(), AdoptionStatus.Pending).forEach(other -> {
            if (!other.getId().equals(req.getId())) {
                other.setStatus(AdoptionStatus.Rejected);
                other.setDecidedAt(LocalDateTime.now());
                other.setDecisionReason("Auto-rechazada: otra solicitud aprobada");
            }
        });

        return toDto(req);
    }

    @Override
    @Transactional
    public AdoptionRequestDto reject(Long id, DecisionDto reasonMaybe) {
        AdoptionRequest req = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request " + id + " not found"));
        if (req.getStatus() != AdoptionStatus.Pending)
            throw new IllegalArgumentException("La solicitud no está pendiente");

        req.setStatus(AdoptionStatus.Rejected); // ← corregido
        req.setDecidedAt(LocalDateTime.now());
        req.setDecisionReason(reasonMaybe != null ? reasonMaybe.reason() : null);
        return toDto(req);
    }

    @Override
    @Transactional
    public AdoptionRequestDto cancel(Long id, DecisionDto reasonMaybe) {
        AdoptionRequest req = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request " + id + " not found"));
        if (req.getStatus() == AdoptionStatus.Approved || req.getStatus() == AdoptionStatus.Rejected)
            throw new IllegalArgumentException("No se puede cancelar una solicitud ya decidida");

        req.setStatus(AdoptionStatus.Canceled);
        req.setDecidedAt(LocalDateTime.now());
        req.setDecisionReason(reasonMaybe != null ? reasonMaybe.reason() : null);
        return toDto(req);
    }

    private AdoptionRequestDto toDto(AdoptionRequest r) {
        return new AdoptionRequestDto(
                r.getId(),
                r.getAdopter().getId(),
                r.getPet().getId(),
                r.getStatus(),
                r.getMessage(),
                r.getDecidedByUserId(),
                r.getDecisionReason(),
                r.getDecidedAt(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }
}
