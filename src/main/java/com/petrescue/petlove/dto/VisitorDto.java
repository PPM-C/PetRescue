package com.petrescue.petlove.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorDto {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotNull  private LocalDateTime appointmentAt;
    private String notes;
}
