package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.AdoptionStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdoptionRequestResponseDto {
    private Long id;
    private Long adopterId;
    private Long petId;
    private String message;
    private AdoptionStatus status;
    private Long decidedByUserId;
    private String decisionReason;
    private LocalDateTime decidedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
