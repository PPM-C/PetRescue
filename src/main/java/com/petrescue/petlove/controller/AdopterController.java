package com.petrescue.petlove.controller;

import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.service.Interface.AdopterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adopters")
public class AdopterController {

    private final AdopterService service;

    @GetMapping
    public Page<AdopterDto> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public AdopterDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<AdopterDto> create(@Valid @RequestBody AdopterCreateDto dto) {
        var created = service.create(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public AdopterDto update(@PathVariable Long id, @Valid @RequestBody AdopterCreateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
