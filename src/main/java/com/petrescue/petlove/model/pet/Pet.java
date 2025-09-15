package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.*;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.model.shelter.Shelter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING) @NotNull
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING) @NotNull
    @Column(nullable = false)
    private Size size;

    @PastOrPresent @NotNull
    @Column(nullable = false)
    private LocalDate arrivalDate;

    @PastOrPresent
    private LocalDate departureDate;

    @Enumerated(EnumType.STRING) @NotNull
    @Column(nullable = false)
    private PetStatus status = PetStatus.Adoptable; // ← default coherente

    @Enumerated(EnumType.STRING) @NotNull
    @Column(nullable = false)
    private Temperament temperament;

    @Enumerated(EnumType.STRING)
    private EnergyLevel energyLevel;

    private Boolean goodWithDogs = true;
    private Boolean goodWithCats = true;
    private Boolean neutered = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @AssertTrue(message = "departureDate solo existe con status=Adopted")
    public boolean isDepartureDateValid() {
        return (status == PetStatus.Adopted && departureDate != null)
                || (status == PetStatus.Adoptable && departureDate == null);
    }

    @AssertTrue(message = "arrivalDate debe ser <= departureDate")
    public boolean isDateOrderValid() {
        return departureDate == null || !arrivalDate.isAfter(departureDate);
    }
}
