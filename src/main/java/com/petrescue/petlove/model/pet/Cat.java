package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.Species;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class Cat extends Pet {
    private String breed;
    private Boolean litterTrained = true;

    @PrePersist @PreUpdate
    private void setSpeciesFlag() { setSpecies(Species.Cat);}
}
