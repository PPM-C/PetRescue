package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.PetCreateDto;
import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.dto.PetUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetService {
    Page<PetDto> List (Pageable pageable);
    PetDto get (long id);
    PetDto create(PetCreateDto dto);
    PetDto update(long id, PetUpdateDto dto);
    void delete(long id);
}
