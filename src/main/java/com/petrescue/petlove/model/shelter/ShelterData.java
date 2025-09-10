package com.petrescue.petlove.model.shelter;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable

public class ShelterData {

    @NotBlank
    private String  name;

    private String  address;

    @Email
    private String  email;

    private String  phone;

    private String  website;

}
