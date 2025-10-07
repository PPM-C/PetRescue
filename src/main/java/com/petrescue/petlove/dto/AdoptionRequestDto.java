package com.petrescue.petlove.dto;

import com.petrescue.petlove.enums.AdoptionStatus;
import lombok.Data;

@Data
public class AdoptionRequestDto {
    private Long id;
    private Long adopterId;
    private Long petId;
    private String message;
    private AdoptionStatus status;
}
