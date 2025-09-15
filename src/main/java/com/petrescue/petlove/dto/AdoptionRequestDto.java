package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.AdoptionStatus;

import java.time.LocalDateTime;

public record AdoptionRequestDto(
        Long id,
        Long adopterId,
        Long petId,
        AdoptionStatus status,
        String message,
        Long decidedByUserId,
        String decisionReason,
        LocalDateTime decidedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
