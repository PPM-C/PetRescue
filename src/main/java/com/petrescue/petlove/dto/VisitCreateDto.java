package com.petrescue.petlove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitCreateDto {
    @NotNull private Long requestId;
    @NotNull private LocalDateTime scheduledAt;
    private String notes;
}
