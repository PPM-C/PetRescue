package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.PetStatus;
import com.petrescue.petlove.enums.Species;
import com.petrescue.petlove.enums.Sex;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.enums.Temperament;
import com.petrescue.petlove.enums.EnergyLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Pet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species;  

    @Enumerated(EnumType.STRING)
    private Sex sex;         

    @Enumerated(EnumType.STRING)
    private Size size;         

    @Enumerated(EnumType.STRING)
    private Temperament temperament; 

    @Enumerated(EnumType.STRING)
    private EnergyLevel energyLevel; 

    private Boolean goodWithDogs;
    private Boolean goodWithCats;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus status = PetStatus.AVAILABLE;

    private LocalDate departureDate;
}
