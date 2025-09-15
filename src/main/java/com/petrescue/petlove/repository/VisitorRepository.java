package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.people.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
