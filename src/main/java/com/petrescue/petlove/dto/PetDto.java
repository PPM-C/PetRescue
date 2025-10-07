package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.*;
import java.time.LocalDate;

public record PetDto(
        Long id,
        Species species,
        String name,
        Sex sex,
        Size size,
        PetStatus status,
        Temperament temperament,
        EnergyLevel energyLevel,
        Boolean goodWithDogs,
        Boolean goodWithCats,
        Boolean neutered,
        LocalDate arrivalDate,
        LocalDate departureDate,
        Long shelterId,
        String notes,
        String photoUrl
) {}
