package com.petrescue.petlove.model.shelter;

import com.petrescue.petlove.enums.VisitStatus;
import com.petrescue.petlove.model.adoption.AdoptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
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
@Table(name = "visits",
        indexes= @Index(name = "ix_visit_request", columnList = "adoption_request_id"))

public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "adoption_request_id", nullable = false)
    @ToString.Exclude
    private AdoptionRequest adoptionRequest;

    @NotNull
    @FutureOrPresent
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull
    private VisitStatus status= VisitStatus.Scheduled;

    @Column(length = 1000)
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @AssertTrue(message = "scheduledAt debe ser futuro si la visita está Scheduled")
    private boolean isScheduledConsistent() {
        if (status != VisitStatus.SCHEDULED || scheduledAt == null) return true;
        return !scheduledAt.isBefore(LocalDateTime.now());
    }


}
