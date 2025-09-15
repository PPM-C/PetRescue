package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.VisitStatus;

import java.time.LocalDateTime;

public record VisitDto(
        Long id,
        Long adoptionRequestId,
        VisitStatus status,
        LocalDateTime scheduledAt,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
