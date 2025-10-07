package com.petrescue.petlove.model.adoption;

import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.model.pet.Pet;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "adoption_requests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AdoptionRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private Adopter adopter;
    @ManyToOne(optional = false) private Pet pet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdoptionStatus status;

    private String message;

    // decisión
    private Long decidedByUserId;
    private String decisionReason;
    private Instant decidedAt;

    // timestamps comunes (si los tienes con @PrePersist / @PreUpdate, mantenlos)
    private Instant createdAt;
    private Instant updatedAt;
}
