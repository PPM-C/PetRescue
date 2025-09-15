package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.dto.VisitorCreateDto;
import com.petrescue.petlove.dto.VisitorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitorService {
    Page <VisitorDto> list (Pageable pageable);
    VisitorDto get (Long id);
    VisitorDto create(VisitorCreateDto dto);
    VisitorDto update(Long id, VisitorCreateDto dto);
    void delete(Long id);

    AdopterDto convertToAdopter (Long visitorId);
}
