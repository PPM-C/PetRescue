package com.petrescue.petlove.controller;

import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.dto.VisitorCreateDto;
import com.petrescue.petlove.dto.VisitorDto;
import com.petrescue.petlove.service.Interface.VisitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorService service;

    @GetMapping
    public Page<VisitorDto> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public VisitorDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<VisitorDto> create(@Valid @RequestBody VisitorCreateDto dto) {
        var created = service.create(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public VisitorDto update(@PathVariable Long id, @Valid @RequestBody VisitorCreateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/convert-to-adopter")
    public AdopterDto convertToAdopter(@PathVariable Long id) {
        return service.convertToAdopter(id);
    }
}

