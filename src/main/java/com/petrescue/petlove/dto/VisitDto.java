package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.VisitStatus;

import java.time.LocalDateTime;

public record VisitDto(
        Long id,
        Long requestId,
        LocalDateTime scheduledAt,
        VisitStatus status,
        String notes
) {}
