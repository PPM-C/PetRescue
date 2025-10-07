package com.petrescue.petlove.dto;

import java.time.LocalDateTime;


public record VisitorCreateDto(
        Long requestId,
        LocalDateTime scheduledAt,
        String notes
) {}
