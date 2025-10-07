package com.petrescue.petlove.dto;

import lombok.Data;

@Data
public class DecisionDto {
    private String reason;
    private Long decidedByUserId;
}
