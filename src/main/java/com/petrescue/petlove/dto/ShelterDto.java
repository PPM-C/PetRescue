package com.petrescue.petlove.dto;

public record ShelterDto(
        Long id,
        String name,
        String email,
        String phone,
        String website,
        String address
) { }
