package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.adoption.Adopter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Page<Adopter> findByNameContainingIgnoreCase(String q, Pageable pageable);
    Adopter findFirstByEmailIgnoreCase(String email);
}
