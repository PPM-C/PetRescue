package com.petrescue.petlove.repository;

import com.petrescue.petlove.model.shelter.Shelter;
import com.petrescue.petlove.model.shelter.ShelterData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ShelterRepositoryTest {

    @Autowired ShelterRepository repo;

    @Test
    void save_and_find_embeddedData_ok() {
        var s = Shelter.builder()
                .data(ShelterData.builder()
                        .name("Refugio Central")
                        .email("info@refugio.com")
                        .address("Calle 1")
                        .phone("600000000")
                        .website("https://refugio.com")
                        .build())
                .build();

        var saved = repo.save(s);
        var found = repo.findById(saved.getId()).orElseThrow();

        assertThat(found.getData().getName()).isEqualTo("Refugio Central");
        assertThat(found.getData().getEmail()).isEqualTo("info@refugio.com");
    }
}
