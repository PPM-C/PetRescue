package com.petrescue.petlove.controller;

import com.petrescue.petlove.api.PageResponse;
import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.dto.VisitDto;
import com.petrescue.petlove.dto.VisitUpdateDto;
import com.petrescue.petlove.service.interfaces.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class VisitController {

  private final VisitService service;

  @GetMapping
  public PageResponse<VisitDto> list(@RequestParam(value="requestId",required=false) Long requestId,
                                     @RequestParam(value="status",required=false) String status,
                                     Pageable pageable){
    return PageResponse.of(service.list(requestId, status, pageable));
  }

  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public VisitDto create(@Valid @RequestBody VisitCreateDto dto){
    return service.create(dto);
  }

  @PutMapping("/{id}")
  public VisitDto update(@PathVariable Long id, @Valid @RequestBody VisitUpdateDto dto){
    return service.update(id, dto);
  }

  @PostMapping("/{id}/cancel")
  public VisitDto cancel(@PathVariable Long id){
    return service.cancel(id);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id){
    service.delete(id);
  }
}
