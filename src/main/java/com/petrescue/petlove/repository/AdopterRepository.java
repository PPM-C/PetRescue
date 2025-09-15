package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.people.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
}
