package com.petrescue.petlove.dto;

import jakarta.validation.constraints.NotNull;

public record AdoptionRequestCreateDto(
        @NotNull Long adopterId,
        @NotNull Long petId,
        String message
) { }
