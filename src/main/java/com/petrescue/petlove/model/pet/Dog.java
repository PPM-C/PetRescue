package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.Species;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dogs")
public class Dog extends Pet {
    private String breed;
    private Boolean isTrained = false;

    @PrePersist @PreUpdate
    private void setSpeciesFlag() { setSpecies(Species.Dog);}
}
