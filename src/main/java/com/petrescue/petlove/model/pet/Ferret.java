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
@Table(name = "ferrets")
public class Ferret extends Pet {
    private String breed;
    private Boolean odorControlTrained = false;

    @PrePersist @PreUpdate
    private void setSpeciesFlag() { setSpecies(Species.Ferret); }
}
