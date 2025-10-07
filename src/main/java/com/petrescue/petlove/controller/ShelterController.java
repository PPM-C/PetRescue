package com.petrescue.petlove.controller;

import com.petrescue.petlove.dto.ShelterCreateDto;
import com.petrescue.petlove.dto.ShelterDto;
import com.petrescue.petlove.service.interfaces.ShelterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shelters")
public class ShelterController {

    private final ShelterService service;

    @GetMapping
    public Page<ShelterDto> list(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ShelterDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ShelterDto> create(@Valid @RequestBody ShelterCreateDto dto) {
        var created = service.create(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ShelterDto update(@PathVariable Long id, @Valid @RequestBody ShelterCreateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
