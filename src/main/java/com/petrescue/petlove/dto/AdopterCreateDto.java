package com.petrescue.petlove.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdopterCreateDto(
        @NotBlank String name,
        @Email String email,
        String phone
) {}
