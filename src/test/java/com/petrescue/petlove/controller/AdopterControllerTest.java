package com.petrescue.petlove.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.service.Interface.AdopterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdopterController.class)
class AdopterControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean AdopterService service;

    @Test
    void create_returns201_andBody() throws Exception {
        var in = new AdopterCreateDto();
        in.setFirstName("Ana");
        in.setLastName("García");
        in.setDocId("DNI-1");
        in.setEmail("ana@example.com");
        in.setPhone("600000000");
        in.setBirthDate(LocalDate.of(1994,5,12));

        var out = new AdopterDto(
                1L,                    // id (Long)
                "Ana",                 // firstName
                "García",              // lastName
                "DNI-1",               // docId
                "ana@example.com",     // email
                "600000000",           // phone
                "C/ Falsa 123",        // <-- address (el String que faltaba)
                LocalDate.of(1994,5,12),
                LocalDateTime.now(),
                LocalDateTime.now()
        );


        when(service.create(any(AdopterCreateDto.class))).thenReturn(out);

        mvc.perform(post("/api/adopters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("ana@example.com"));
    }
}
