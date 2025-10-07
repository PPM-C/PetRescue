package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.pet.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
  Page<Pet> findByNameContainingIgnoreCase(String q, Pageable pageable);
  Pet findFirstByNameIgnoreCase(String name);
}
