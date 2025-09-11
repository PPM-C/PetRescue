package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Sex sex;

    @Enumerated(EnumType.STRING) @NotNull
    private Size size;

    @PastOrPresent @NotNull
    private LocalDate arrivalDate;

    @PastOrPresent
    private LocalDate departureDate;

    @Enumerated(EnumType.STRING) @NotNull
    private PetStatus status;

    @Enumerated(EnumType.STRING) @NotNull
    private Temperament temperament;

    @Enumerated(EnumType.STRING)
    private EnergyLevel energyLevel;

    private Boolean goodWithDogs= true;
    private Boolean goodWithCats= true;
    private Boolean neutered = false;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Species species;

    @AssertTrue(message = "departureDate solo existe con status=Adopted")
    public boolean isdepartureDateValid() {
        return (status == PetStatus.Adopted && departureDate != null)
                || (status == PetStatus.Adoptable && departureDate == null);}

    @AssertTrue(message = "arrivalDate debe ser <= departureDate")
    public boolean isDateOrderValid() {
        return departureDate == null || !arrivalDate.isAfter(departureDate);
    }



}
