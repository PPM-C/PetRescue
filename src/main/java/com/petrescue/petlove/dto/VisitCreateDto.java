package com.petrescue.petlove.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VisitCreateDto(
        @NotNull @FutureOrPresent LocalDateTime scheduledAt,
        String notes
        ) {
}
