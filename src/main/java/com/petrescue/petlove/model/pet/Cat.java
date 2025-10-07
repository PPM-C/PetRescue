package com.petrescue.petlove.model.pet;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cats")
@Getter @Setter @NoArgsConstructor
public class Cat extends Pet {
    private Boolean litterTrained;
}
