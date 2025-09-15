package com.petrescue.petlove.model.pet;

import com.petrescue.petlove.enums.Species;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cats")
public class Cat extends Pet {
    private String breed;
    private Boolean litterTrained = true;
    private Boolean isIndoor = true;

    @PrePersist @PreUpdate
    private void setSpeciesFlag() { setSpecies(Species.Cat); }
}
