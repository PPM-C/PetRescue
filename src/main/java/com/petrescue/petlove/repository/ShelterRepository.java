package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.shelter.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
