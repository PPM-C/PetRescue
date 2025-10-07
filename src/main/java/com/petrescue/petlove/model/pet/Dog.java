package com.petrescue.petlove.model.pet;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dogs")
@Getter @Setter @NoArgsConstructor
public class Dog extends Pet {
    private String breed;
    private boolean trained;
}
