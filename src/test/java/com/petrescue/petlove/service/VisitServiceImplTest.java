package com.petrescue.petlove.service;

import com.petrescue.petlove.dto.VisitCreateDto;
import com.petrescue.petlove.enums.AdoptionStatus;
import com.petrescue.petlove.enums.VisitStatus;
import com.petrescue.petlove.model.adoption.AdoptionRequest;
import com.petrescue.petlove.model.shelter.Visit;
import com.petrescue.petlove.repository.AdoptionRequestRepository;
import com.petrescue.petlove.repository.VisitRepository;
import com.petrescue.petlove.service.Imp.VisitServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {

    @Mock VisitRepository repo;
    @Mock AdoptionRequestRepository reqRepo;
    @InjectMocks VisitServiceImpl service;

    @Test
    void schedule_happyPath_createsScheduledVisit() {
        var req = AdoptionRequest.builder().id(5L).status(AdoptionStatus.Pending).build();
        when(reqRepo.findById(5L)).thenReturn(Optional.of(req));
        var dto = new VisitCreateDto(LocalDateTime.now().plusDays(2), "primera");

        var out = service.schedule(5L, dto);

        assertThat(out.status()).isEqualTo(VisitStatus.Scheduled);
        assertThat(out.scheduledAt()).isAfter(LocalDateTime.now());
        verify(repo).save(any(Visit.class));
    }

    @Test
    void schedule_pastDate_throwsIAE() {
        var req = AdoptionRequest.builder().id(5L).status(AdoptionStatus.Pending).build();
        when(reqRepo.findById(5L)).thenReturn(Optional.of(req));
        var dto = new VisitCreateDto(LocalDateTime.now().minusHours(1), "pasado");

        assertThatThrownBy(() -> service.schedule(5L, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("futuro");
    }

    @Test
    void schedule_whenRequestNotPending_throwsIAE() {
        var req = AdoptionRequest.builder().id(5L).status(AdoptionStatus.Approved).build();
        when(reqRepo.findById(5L)).thenReturn(Optional.of(req));
        var dto = new VisitCreateDto(LocalDateTime.now().plusDays(1), null);

        assertThatThrownBy(() -> service.schedule(5L, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("PENDING");
    }

    @Test
    void schedule_requestMissing_throws404() {
        when(reqRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.schedule(99L, new VisitCreateDto(LocalDateTime.now().plusDays(1), null)))
                .isInstanceOf(EntityNotFoundException.class);
    }
}

