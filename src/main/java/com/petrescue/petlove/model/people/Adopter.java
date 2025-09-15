package com.petrescue.petlove.model.people;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "adopters")

public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    @Email @NotBlank @Column (unique = true)
    private String  email;

    @NotBlank
    private String  phone;

    @Embedded @Valid
    private PersonalData personalData;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @AssertTrue (message = "El adoptante debe ser mayor de 18 años")
    private boolean isAdult(){
        if (personalData==null || personalData.getBirthDate()==null) return false;
        return !personalData.getBirthDate().plusYears(18).isAfter(LocalDate.now());
    }
}
