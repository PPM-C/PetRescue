package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PetCreateDto (
        @NotNull Species species,
        @NotBlank String name,
        @NotNull Sex sex,
        @NotNull Size size,
        @NotNull @PastOrPresent LocalDate arrivalDate,
        @NotNull Temperament temperament,
        EnergyLevel energyLevel,
        Boolean goodWithDogs,
        Boolean goodWithCats,
        Boolean neutered,
        Long shelterId,

        //especificos
        String breed,
        Boolean isTrained, //dog
        Boolean litterTrained, //cat
        Boolean odorControlTrained //ferret
        ){ }
