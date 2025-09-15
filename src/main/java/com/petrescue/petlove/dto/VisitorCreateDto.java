package com.petrescue.petlove.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VisitorCreateDto (
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull @FutureOrPresent LocalDateTime appointmentAt
        ){
}
