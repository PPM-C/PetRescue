package com.petrescue.petlove.service.interfaces;

import com.petrescue.petlove.dto.PetCreateDto;
import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.dto.PetUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetService {
  Page<PetDto> list(String search, Pageable pageable);
  PetDto get(long id);
  PetDto create(PetCreateDto dto);
  PetDto update(long id, PetUpdateDto dto);
  void delete(long id);
}
