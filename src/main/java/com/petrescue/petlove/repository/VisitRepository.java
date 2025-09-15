package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.shelter.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByAdoptionRequest_Id(Long requestId);
}
