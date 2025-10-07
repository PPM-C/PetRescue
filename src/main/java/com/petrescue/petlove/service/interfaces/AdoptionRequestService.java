package com.petrescue.petlove.service.interfaces;

import com.petrescue.petlove.dto.AdoptionRequestCreateDto;
import com.petrescue.petlove.dto.AdoptionRequestDto;
import com.petrescue.petlove.dto.DecisionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdoptionRequestService {
    Page<AdoptionRequestDto> list(Long petId, Long adopterId, String status, Pageable pageable);
    AdoptionRequestDto create(AdoptionRequestCreateDto dto);
    AdoptionRequestDto approve(Long id, DecisionDto reasonMaybe);
    AdoptionRequestDto reject(Long id, DecisionDto reasonMaybe);
    AdoptionRequestDto cancel(Long id, DecisionDto reasonMaybe);
}
