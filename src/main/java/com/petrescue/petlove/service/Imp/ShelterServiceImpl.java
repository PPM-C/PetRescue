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
    public Page <ShelterDto> list (Pageable pageable){
        return repo.findAll(pageable).map(this::toDto);
    }

    @Override
    public ShelterDto get (Long id){
        return repo.findAllById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Shelter " + id + " not found"));
    }

    @Override
    @Transactional
    public ShelterDto create(ShelterCreateDto dto) {
        Shelter s = Shelter.builder()
                .data(ShelterData.builder())
    }
}
