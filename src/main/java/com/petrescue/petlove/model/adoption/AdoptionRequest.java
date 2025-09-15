package com.petrescue.petlove.model.adoption;

import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.model.people.Adopter;
import com.petrescue.petlove.model.pet.Pet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "adoption_requests",
        indexes = {
                @Index(name = "ix_adoption_req_pet", columnList = "pet_id"),
                @Index(name = "ix_adoption", columnList = "adopter_id")
        }
)
public class AdoptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "adopter_id",nullable = false)
    @ToString.Exclude
    private Adopter adopter;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @ToString.Exclude
    private Pet pet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    @NotNull
    @ToString.Include
    private AdoptionStatus status=AdoptionStatus.Pending;

    @Column(length = 1000)
    private String message;

    // Datos de decisión
    private Long decidedByUserId;

    @Column(length = 500)
    private String decisionReason;

    private LocalDateTime decidedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
