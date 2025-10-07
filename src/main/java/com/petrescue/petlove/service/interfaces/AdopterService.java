package com.petrescue.petlove.service.interfaces;

import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.dto.AdopterUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdopterService {
  Page<AdopterDto> list(String search, Pageable pageable);
  AdopterDto get(Long id);
  AdopterDto create(AdopterCreateDto dto);
  AdopterDto update(Long id, AdopterUpdateDto dto);
  void delete(Long id);
}
