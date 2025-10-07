package com.petrescue.petlove.service.interfaces;

import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.dto.VisitDto;
import com.petrescue.petlove.dto.VisitUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitService {
  Page<VisitDto> list(Long requestId, String status, Pageable pageable);
  VisitDto create(VisitCreateDto dto);
  VisitDto update(Long visitId, VisitUpdateDto dto);
  VisitDto cancel(Long visitId);
  void delete(Long visitId);
}
