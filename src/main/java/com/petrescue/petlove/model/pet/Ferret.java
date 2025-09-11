package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.Species;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class Ferret extends Pet{
    private String breed;
    private Boolean odorControlTrained = false;

    @PrePersist @PreUpdate
    private void setSpeciesFlag() { setSpecies(Species.Ferret);}
}
