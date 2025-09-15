package com.petrescue.petlove.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdopterDto (
        Long id,
        String firstName,
        String lastName,
        String docId,
        String email,
        String phone,
        LocalDate birthDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
