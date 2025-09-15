package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.enums.EnergyLevel;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.enums.Species;

public interface PetMatchService {
    PetDto suggestRandom (Species species, EnergyLevel energyLevel, Size size);
}
