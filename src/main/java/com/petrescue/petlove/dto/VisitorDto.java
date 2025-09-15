package com.petrescue.petlove.dto;

import java.time.LocalDateTime;

public record VisitorDto(
        Long id,
        String firstName,
        String lastName,
        LocalDateTime appointmentAt,
        LocalDateTime createdAt
) {
}
