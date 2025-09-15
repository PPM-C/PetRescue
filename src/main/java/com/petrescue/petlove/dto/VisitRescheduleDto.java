package com.petrescue.petlove.dto;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;

public record VisitRescheduleDto(
        @FutureOrPresent LocalDateTime scheduledAt,
        String notes
        ) {
}
