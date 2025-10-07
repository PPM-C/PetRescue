package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.adoption.AdoptionRequest;
import com.petrescue.petlove.enums.AdoptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    Page<AdoptionRequest> findByPetId(Long petId, Pageable pageable);
    Page<AdoptionRequest> findByAdopterId(Long adopterId, Pageable pageable);
    Page<AdoptionRequest> findByPetIdAndAdopterId(Long petId, Long adopterId, Pageable pageable);
    Page<AdoptionRequest> findByPetIdAndStatus(Long petId, AdoptionStatus status, Pageable pageable);
    Page<AdoptionRequest> findByAdopterIdAndStatus(Long adopterId, AdoptionStatus status, Pageable pageable);
    Page<AdoptionRequest> findByPetIdAndAdopterIdAndStatus(Long petId, Long adopterId, AdoptionStatus status, Pageable pageable);
}
