package com.petrescue.petlove.service.impl;

import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.dto.VisitorCreateDto;
import com.petrescue.petlove.dto.VisitorDto;
import com.petrescue.petlove.model.adoption.Adopter;
import com.petrescue.petlove.model.people.PersonalData;
import com.petrescue.petlove.model.people.Visitor;
import com.petrescue.petlove.repository.AdopterRepository;
import com.petrescue.petlove.repository.VisitorRepository;
import com.petrescue.petlove.service.interfaces.ShelterService;
import com.petrescue.petlove.service.interfaces.VisitService;
import com.petrescue.petlove.service.interfaces.VisitorService;
import com.petrescue.petlove.service.interfaces.AdopterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repo;
    private final AdopterRepository adopterRepo;

    @Override
    public Page<VisitorDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }

    @Override
    public VisitorDto get(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Visitor " + id + " not found"));
    }

    @Override
    @Transactional
    public VisitorDto create(VisitorCreateDto dto) {
        Visitor v = Visitor.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getlastName())
                .appointmentAt(dto.getappointmentAt())
                .build();
        return toDto(repo.save(v));
    }

    @Override
    @Transactional
    public VisitorDto update(Long id, VisitorCreateDto dto) {
        Visitor v = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitor " + id + " not found"));
        v.setFirstName(dto.getfirstName());
        v.setLastName(dto.getlastName());
        v.setAppointmentAt(dto.getAppointmentAt());
        return toDto(v);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Visitor " + id + " not found");
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public AdopterDto convertToAdopter(Long visitorId) {
        Visitor v = repo.findById(visitorId)
                .orElseThrow(() -> new EntityNotFoundException("Visitor " + visitorId + " not found"));
        Adopter a = Adopter.builder()
                .avatarUrl(v.getAvatarUrl())
                .email(("visitor" + v.getId() + "@unknown.local"))
                .phone("000000000")
                .personalData(PersonalData.builder()
                        .firstName(v.getFirstName())
                        .lastName(v.getLastName())
                        .birthDate(LocalDate.now().minusYears(25))
                        .build())
                .build();
        a = adopterRepo.save(a);
        return new AdopterDto(
                a.getId(),
                a.getPersonalData().getFirstName(),
                a.getPersonalData().getLastName(),
                a.getEmail(),
                a.getPhone(),
                a.getAvatarUrl(),
                a.getPersonalData().getDocId(),
                a.getPersonalData().getBirthDate(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }

    private VisitorDto toDto(Visitor v) {
        return new VisitorDto(
                v.getId(),
                v.getFirstName(),
                v.getLastName(),
                v.getAppointmentAt(),
                v.getCreatedAt()
        );
    }
}
