package com.petrescue.petlove.controller;

import com.petrescue.petlove.api.PageResponse;
import com.petrescue.petlove.dto.PetCreateDto;
import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.dto.PetUpdateDto;
import com.petrescue.petlove.service.interfaces.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class PetController {

  private final PetService service;

  @GetMapping
  public PageResponse<PetDto> list(@RequestParam(value="search",required=false) String search,
                                   Pageable pageable){
    return PageResponse.of(service.list(search, pageable));
  }

  @GetMapping("/{id}")
  public PetDto get(@PathVariable long id){ return service.get(id); }

  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public PetDto create(@Valid @RequestBody PetCreateDto dto){ return service.create(dto); }

  @PutMapping("/{id}")
  public PetDto update(@PathVariable long id, @Valid @RequestBody PetUpdateDto dto){
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id){ service.delete(id); }
}
