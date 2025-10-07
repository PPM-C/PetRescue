package com.petrescue.petlove.repository;

import com.petrescue.petlove.enums.VisitStatus;
import com.petrescue.petlove.model.shelter.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
  Page<Visit> findByStatus(VisitStatus status, Pageable pageable);
  Page<Visit> findByRequest_Id(Long requestId, Pageable pageable);
}
