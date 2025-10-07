package com.petrescue.petlove.service.impl;

import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.dto.AdopterUpdateDto;
import com.petrescue.petlove.model.adoption.Adopter;
import com.petrescue.petlove.repository.AdopterRepository;
import com.petrescue.petlove.service.interfaces.AdopterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class AdopterServiceImpl implements AdopterService {

  private final AdopterRepository repo;

  private AdopterDto toDto(Adopter a){
    return new AdopterDto(a.getId(), a.getName(), a.getEmail(), a.getPhone());
  }

  @Override @Transactional(readOnly = true)
  public Page<AdopterDto> list(String search, Pageable pageable){
    Page<Adopter> p = (search==null || search.isBlank())
      ? repo.findAll(pageable)
      : repo.findByNameContainingIgnoreCase(search, pageable);
    return p.map(this::toDto);
  }

  @Override @Transactional(readOnly = true)
  public AdopterDto get(Long id){
    return repo.findById(id).map(this::toDto)
      .orElseThrow(() -> new EntityNotFoundException("Adopter "+id+" not found"));
  }

  @Override @Transactional
  public AdopterDto create(AdopterCreateDto dto){
    Adopter a = new Adopter();
    a.setName(dto.name().trim());
    a.setEmail(dto.email());
    a.setPhone(dto.phone());
    return toDto(repo.save(a));
  }

  @Override @Transactional
  public AdopterDto update(Long id, AdopterUpdateDto dto){
    Adopter a = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Adopter "+id+" not found"));
    if (dto.name()!=null)  a.setName(dto.name().trim());
    if (dto.email()!=null) a.setEmail(dto.email());
    if (dto.phone()!=null) a.setPhone(dto.phone());
    return toDto(a);
  }

  @Override @Transactional
  public void delete(Long id){
    if (!repo.existsById(id)) throw new EntityNotFoundException("Adopter "+id+" not found");
    repo.deleteById(id);
  }
}
