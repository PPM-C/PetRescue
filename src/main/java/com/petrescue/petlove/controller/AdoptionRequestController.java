package com.petrescue.petlove.controller;

import com.petrescue.petlove.api.PageResponse;
import com.petrescue.petlove.dto.AdoptionRequestCreateDto;
import com.petrescue.petlove.dto.AdoptionRequestDto;
import com.petrescue.petlove.dto.DecisionDto;
import com.petrescue.petlove.service.interfaces.AdoptionRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adoption-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class AdoptionRequestController {

  private final AdoptionRequestService service;

  // Tablero admin + filtros opcionales
  @GetMapping
  public PageResponse<AdoptionRequestDto> list(
      @RequestParam(value="petId", required=false) Long petId,
      @RequestParam(value="adopterId", required=false) Long adopterId,
      @RequestParam(value="status", required=false) String status,
      Pageable pageable) {
    return PageResponse.of(service.list(petId, adopterId, status, pageable));
  }

  // Form público (user/admin): puede mandar petName en vez de petId
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public AdoptionRequestDto create(@Valid @RequestBody AdoptionRequestCreateDto dto){
    return service.create(dto);
  }

  // Acciones admin
  @PostMapping("/{id}/approve")
  public AdoptionRequestDto approve(@PathVariable Long id, @RequestBody(required=false) DecisionDto dto){
    return service.approve(id, dto);
  }

  @PostMapping("/{id}/reject")
  public AdoptionRequestDto reject(@PathVariable Long id, @RequestBody(required=false) DecisionDto dto){
    return service.reject(id, dto);
  }

  @PostMapping("/{id}/cancel")
  public AdoptionRequestDto cancel(@PathVariable Long id, @RequestBody(required=false) DecisionDto dto){
    return service.cancel(id, dto);
  }
}
