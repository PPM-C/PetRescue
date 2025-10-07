package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.EnergyLevel;
import com.petrescue.petlove.enums.Sex;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.enums.Temperament;
import java.time.LocalDate;

public record PetUpdateDto(
        String name,
        Sex sex,
        Size size,
        Temperament temperament,
        EnergyLevel energyLevel,
        Boolean goodWithDogs,
        Boolean goodWithCats,
        Boolean neutered,
        LocalDate arrivalDate,
        String notes,
        String photoUrl
) {}
