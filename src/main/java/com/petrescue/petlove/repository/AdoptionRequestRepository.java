package com.petrescue.petlove.repository;

import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.model.adoption.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {

    // Si quieres chequear existencia con múltiples estados (lo usamos en el service):
    boolean existsByAdopter_IdAndPet_IdAndStatusIn(Long adopterId, Long petId, Collection<AdoptionStatus> statuses);

    // Listados auxiliares
    List<AdoptionRequest> findByPet_IdAndStatus(Long petId, AdoptionStatus status);
    List<AdoptionRequest> findByAdopter_Id(Long adopterId);
    List<AdoptionRequest> findByPet_Id(Long petId);
}
