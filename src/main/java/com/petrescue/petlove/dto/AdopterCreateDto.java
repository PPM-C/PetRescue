package com.petrescue.petlove.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AdopterCreateDto {
        @NotBlank private String firstName;
        @NotBlank private String lastName;
        @NotBlank private String docId;
        @Email @NotBlank private String email;
        @NotBlank private String phone;
        @Past @NotNull private LocalDate birthDate;
}
