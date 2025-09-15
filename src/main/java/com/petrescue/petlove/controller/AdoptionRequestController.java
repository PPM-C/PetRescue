package com.petrescue.petlove.controller;

import com.petrescue.petlove.dto.AdoptionRequestCreateDto;
import com.petrescue.petlove.dto.AdoptionRequestDto;
import com.petrescue.petlove.dto.DecisionDto;
import com.petrescue.petlove.service.Interface.AdoptionRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adoption-requests")
public class AdoptionRequestController {

    private final AdoptionRequestService service;

    @GetMapping
    public Page<AdoptionRequestDto> list(
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) Long adopterId,
            Pageable pageable
    ) {
        return service.list(petId, adopterId, pageable);
    }

    @PostMapping
    public ResponseEntity<AdoptionRequestDto> create(@Valid @RequestBody AdoptionRequestCreateDto dto) {
        var created = service.create(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PostMapping("/{id}/approve")
    public AdoptionRequestDto approve(@PathVariable Long id, @RequestBody(required = false) DecisionDto reason) {
        return service.approve(id, reason);
    }

    @PostMapping("/{id}/reject")
    public AdoptionRequestDto reject(@PathVariable Long id, @RequestBody(required = false) DecisionDto reason) {
        return service.reject(id, reason);
    }

    @PostMapping("/{id}/cancel")
    public AdoptionRequestDto cancel(@PathVariable Long id, @RequestBody(required = false) DecisionDto reason) {
        return service.cancel(id, reason);
    }
}
