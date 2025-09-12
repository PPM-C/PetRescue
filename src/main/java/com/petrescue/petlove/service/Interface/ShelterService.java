package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.ShelterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelterService {
    Page<ShelterDto> findAll(Pageable pageable);
    ShelterDto get (Long id);
    ShelterDto create(ShelterDto dtd);
    ShelterDto update(Long id, ShelterDto dto);
    void delete(Long id);
}
