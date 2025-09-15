package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdopterService {
    Page <AdopterDto> list (Pageable pageable);
    AdopterDto get (Long id);
    AdopterDto create (AdopterCreateDto dto);
    AdopterDto update (Long id, AdopterCreateDto dto);
    void delete (Long id);
}
