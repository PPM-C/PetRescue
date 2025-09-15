package com.petrescue.petlove.service.Imp;

import com.petrescue.petlove.dto.ShelterCreateDto;
import com.petrescue.petlove.dto.ShelterDto;
import com.petrescue.petlove.model.shelter.Shelter;
import com.petrescue.petlove.model.shelter.ShelterData;
import com.petrescue.petlove.repository.ShelterRepository;
import com.petrescue.petlove.service.Interface.ShelterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository repo;

    @Override
    public Page<ShelterDto> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }

    @Override
    public ShelterDto findById(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Shelter " + id + " not found"));
    }

    @Override
    @Transactional
    public ShelterDto create(ShelterCreateDto dto) {
        Shelter s = Shelter.builder()
                .data(ShelterData.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .phone(dto.phone())
                        .website(dto.website())
                        .address(dto.address())
                        .build())
                .build();
        return toDto(repo.save(s));
    }

    @Override
    @Transactional
    public ShelterDto update(Long id, ShelterCreateDto dto) { // ← firma como en la interfaz
        Shelter s = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shelter " + id + " not found"));
        var d = s.getData();
        d.setName(dto.name());
        d.setEmail(dto.email());
        d.setPhone(dto.phone());
        d.setWebsite(dto.website());
        d.setAddress(dto.address());
        return toDto(s);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Shelter " + id + " not found");
        repo.deleteById(id);
    }

    private ShelterDto toDto(Shelter s) {
        var d = s.getData();
        return new ShelterDto(
                s.getId(),
                d.getName(),
                d.getEmail(), // ← corregido (antes d.getName())
                d.getPhone(),
                d.getWebsite(),
                d.getAddress()
        );
    }
}

