package com.petrescue.petlove.service.Interface;

import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.dto.VisitDto;
import com.petrescue.petlove.dto.VisitRescheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitService {
    Page<VisitDto> list(Long requestId, Pageable pageable);
    VisitDto schedule(Long requestId, VisitCreateDto dto);
    VisitDto reschedule(Long visitId, VisitRescheduleDto dto);
    VisitDto cancel(Long visitId, VisitRescheduleDto reasonMaybe);
    VisitDto complete(Long visitId, VisitRescheduleDto notesMaybe);
}
