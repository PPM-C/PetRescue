package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.ShelterCreateDto;
import com.petrescue.petlove.dto.ShelterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelterService {
    Page<ShelterDto> findAll(Pageable pageable);
    ShelterDto findById(Long id);
    ShelterDto create(ShelterCreateDto dto);
    ShelterDto update(Long id, ShelterCreateDto dto);
    void delete(Long id);
}
