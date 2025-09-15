package com.petrescue.petlove.controller;

import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.dto.VisitDto;
import com.petrescue.petlove.dto.VisitRescheduleDto;
import com.petrescue.petlove.service.Interface.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService service;

    @GetMapping
    public Page<VisitDto> list(@RequestParam(required = false) Long requestId, Pageable pageable) {
        return service.list(requestId, pageable);
    }

    @PostMapping("/{requestId}")
    public ResponseEntity<VisitDto> schedule(@PathVariable Long requestId,
                                             @Valid @RequestBody VisitCreateDto dto) {
        return ResponseEntity.ok(service.schedule(requestId, dto));
    }

    @PatchMapping("/{visitId}/reschedule")
    public VisitDto reschedule(@PathVariable Long visitId, @RequestBody VisitRescheduleDto dto) {
        return service.reschedule(visitId, dto);
    }

    @PostMapping("/{visitId}/cancel")
    public VisitDto cancel(@PathVariable Long visitId, @RequestBody(required = false) VisitRescheduleDto reasonMaybe) {
        return service.cancel(visitId, reasonMaybe);
    }

    @PostMapping("/{visitId}/complete")
    public VisitDto complete(@PathVariable Long visitId, @RequestBody(required = false) VisitRescheduleDto notesMaybe) {
        return service.complete(visitId, notesMaybe);
    }
}
