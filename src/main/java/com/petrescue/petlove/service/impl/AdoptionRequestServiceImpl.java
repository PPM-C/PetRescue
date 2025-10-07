package com.petrescue.petlove.service.impl;

import com.petrescue.petlove.dto.AdoptionRequestDto;
import com.petrescue.petlove.dto.AdoptionRequestResponseDto;
import com.petrescue.petlove.dto.DecisionDto;
import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.enums.PetStatus;
import com.petrescue.petlove.model.adoption.AdoptionRequest;
import com.petrescue.petlove.model.adoption.Adopter;
import com.petrescue.petlove.model.pet.Pet;
import com.petrescue.petlove.repository.AdoptionRequestRepository;
import com.petrescue.petlove.repository.AdopterRepository;
import com.petrescue.petlove.repository.PetRepository;
import com.petrescue.petlove.service.interfaces.AdoptionRequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdoptionRequestServiceImpl implements AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;
    private final PetRepository petRepository;
    private final AdopterRepository adopterRepository;

    // ===========================
    //            LIST
    // ===========================
    @Override
    @Transactional(readOnly = true)
    public Page<AdoptionRequestDto> list(Long petId, Long adopterId, String status, Pageable pageable) {
        Page<AdoptionRequest> page;

        if (petId != null) {
            page = adoptionRequestRepository.findByPetId(petId, pageable);
        } else if (adopterId != null) {
            page = adoptionRequestRepository.findByAdopterId(adopterId, pageable);
        } else if (status != null && !status.isBlank()) {
            AdoptionStatus wanted = AdoptionStatus.valueOf(status.toUpperCase());
            page = adoptionRequestRepository.findByStatus(wanted, pageable);
        } else {
            page = adoptionRequestRepository.findAll(pageable);
        }

        return page.map(this::toListDto);
    }

    // ===========================
    //            GET
    // ===========================
    @Override
    public AdoptionRequestResponseDto get(Long id) {
        AdoptionRequest ar = adoptionRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AdoptionRequest not found: " + id));
        return toResponseDto(ar);
    }

    // ===========================
    //          CREATE
    // ===========================
    @Override
    public AdoptionRequestResponseDto create(Long adopterId, Long petId, String message) {
        Adopter adopter = adopterRepository.findById(adopterId)
                .orElseThrow(() -> new EntityNotFoundException("Adopter not found: " + adopterId));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found: " + petId));

        if (pet.getStatus() == PetStatus.ADOPTED) {
            throw new IllegalStateException("Pet is already adopted");
        }

        AdoptionRequest ar = new AdoptionRequest();
        ar.setAdopter(adopter);
        ar.setPet(pet);
        ar.setMessage(message);
        ar.setStatus(AdoptionStatus.PENDING);
        ar.setCreatedAt(nowInstant());

        AdoptionRequest saved = adoptionRequestRepository.save(ar);
        return toResponseDto(saved);
    }

    // ===========================
    //         APPROVE
    // ===========================
    @Override
    public AdoptionRequestResponseDto approve(Long requestId, DecisionDto dto) {
        AdoptionRequest req = adoptionRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("AdoptionRequest not found: " + requestId));

        if (req.getStatus() != AdoptionStatus.PENDING) {
            throw new IllegalStateException("Only PENDING requests can be approved");
        }

        Pet pet = req.getPet();
        if (pet.getStatus() == PetStatus.ADOPTED) {
            throw new IllegalStateException("Pet already marked as ADOPTED");
        }

        // Aprobar esta solicitud
        req.setStatus(AdoptionStatus.APPROVED);
        req.setDecidedAt(nowInstant());
        req.setDecisionReason(dto != null ? dto.getReason() : null);
        req.setDecidedByUserId(dto != null ? dto.getDecidedByUserId() : null);

        // Marcar mascota como adoptada
        pet.setStatus(PetStatus.ADOPTED);
        pet.setDepartureDate(LocalDate.now());

        // Rechazar otras solicitudes del mismo pet
        List<AdoptionRequest> others = adoptionRequestRepository.findByPetId(pet.getId());
        for (AdoptionRequest other : others) {
            if (!Objects.equals(other.getId(), req.getId())) {
                other.setStatus(AdoptionStatus.REJECTED);
                other.setDecidedAt(nowInstant());
                other.setDecisionReason("Auto-rejected because another request was approved");
                other.setDecidedByUserId(dto != null ? dto.getDecidedByUserId() : null);
            }
        }

        return toResponseDto(req);
    }

    // ===========================
    //         REJECT
    // ===========================
    @Override
    public AdoptionRequestResponseDto reject(Long requestId, DecisionDto dto) {
        AdoptionRequest req = adoptionRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("AdoptionRequest not found: " + requestId));

        if (req.getStatus() != AdoptionStatus.PENDING) {
            throw new IllegalStateException("Only PENDING requests can be rejected");
        }

        req.setStatus(AdoptionStatus.REJECTED);
        req.setDecidedAt(nowInstant());
        req.setDecisionReason(dto != null ? dto.getReason() : null);
        req.setDecidedByUserId(dto != null ? dto.getDecidedByUserId() : null);

        return toResponseDto(req);
    }

    // ===========================
    //         CANCEL
    // ===========================
    @Override
    public AdoptionRequestResponseDto cancel(Long requestId, DecisionDto dto) {
        AdoptionRequest req = adoptionRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("AdoptionRequest not found: " + requestId));

        if (req.getStatus() != AdoptionStatus.PENDING) {
            throw new IllegalStateException("Only PENDING requests can be cancelled");
        }

        req.setStatus(AdoptionStatus.CANCELLED);
        req.setDecidedAt(nowInstant());
        req.setDecisionReason(dto != null ? dto.getReason() : null);
        req.setDecidedByUserId(dto != null ? dto.getDecidedByUserId() : null);

        return toResponseDto(req);
    }

    // ===========================
    //         MAPPERS
    // ===========================
    private AdoptionRequestDto toListDto(AdoptionRequest r) {
        AdoptionRequestDto dto = new AdoptionRequestDto();
        dto.setId(r.getId());
        dto.setAdopterId(r.getAdopter() != null ? r.getAdopter().getId() : null);
        dto.setPetId(r.getPet() != null ? r.getPet().getId() : null);
        dto.setStatus(r.getStatus());
        dto.setMessage(r.getMessage());
        return dto;
    }

    private AdoptionRequestResponseDto toResponseDto(AdoptionRequest r) {
        AdoptionRequestResponseDto resp = new AdoptionRequestResponseDto();
        resp.setId(r.getId());
        resp.setAdopterId(r.getAdopter() != null ? r.getAdopter().getId() : null);
        resp.setPetId(r.getPet() != null ? r.getPet().getId() : null);
        resp.setStatus(r.getStatus());
        resp.setMessage(r.getMessage());
        resp.setDecidedByUserId(r.getDecidedByUserId());
        resp.setDecisionReason(r.getDecisionReason());
        resp.setDecidedAt(toLocalDateTime(r.getDecidedAt()));
        resp.setCreatedAt(toLocalDateTime(r.getCreatedAt()));
        resp.setUpdatedAt(toLocalDateTime(r.getUpdatedAt()));
        return resp;
    }

    // ===========================
    //         HELPERS
    // ===========================
    private Instant nowInstant() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
    }

    private static LocalDateTime toLocalDateTime(Instant i) {
        return i == null ? null : LocalDateTime.ofInstant(i, ZoneId.systemDefault());
    }
}
