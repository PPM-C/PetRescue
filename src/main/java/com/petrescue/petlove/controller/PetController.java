package com.petrescue.petlove.controller;

import com.petrescue.petlove.dto.PetCreateDto;
import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.dto.PetUpdateDto;
import com.petrescue.petlove.enums.EnergyLevel;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.enums.Species;
import com.petrescue.petlove.service.Interface.PetMatchService;
import com.petrescue.petlove.service.Interface.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

    private final PetService service;
    private final PetMatchService matchService;

    @GetMapping
    public Page<PetDto> list(Pageable pageable) {
        return service.List(pageable);
    }

    @GetMapping("/{id}")
    public PetDto get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<PetDto> create(@Valid @RequestBody PetCreateDto dto) {
        var created = service.create(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PatchMapping("/{id}")
    public PetDto update(@PathVariable long id, @RequestBody PetUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Sugerencia aleatoria (matching simple)
    @GetMapping("/suggest")
    public PetDto suggest(
            @RequestParam(required = false) Species species,
            @RequestParam(required = false) EnergyLevel energyLevel,
            @RequestParam(required = false) Size size
    ) {
        return matchService.suggestRandom(species, energyLevel, size);
    }
}
