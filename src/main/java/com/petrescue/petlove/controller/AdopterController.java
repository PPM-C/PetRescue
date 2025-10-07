package com.petrescue.petlove.controller;

import com.petrescue.petlove.api.PageResponse;
import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.dto.AdopterUpdateDto;
import com.petrescue.petlove.service.interfaces.AdopterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adopters")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class AdopterController {

  private final AdopterService service;

  @GetMapping
  public PageResponse<AdopterDto> list(@RequestParam(value="search",required=false) String search,
                                       Pageable pageable){
    return PageResponse.of(service.list(search, pageable));
  }

  @GetMapping("/{id}")
  public AdopterDto get(@PathVariable Long id){ return service.get(id); }

  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public AdopterDto create(@Valid @RequestBody AdopterCreateDto dto){ return service.create(dto); }

  @PutMapping("/{id}")
  public AdopterDto update(@PathVariable Long id, @Valid @RequestBody AdopterUpdateDto dto){
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id){ service.delete(id); }
}
