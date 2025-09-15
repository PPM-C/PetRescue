package com.petrescue.petlove.service.Imp;

import com.petrescue.petlove.dto.AdopterCreateDto;
import com.petrescue.petlove.dto.AdopterDto;
import com.petrescue.petlove.model.people.Adopter;
import com.petrescue.petlove.model.people.PersonalData;
import com.petrescue.petlove.repository.AdopterRepository;
import com.petrescue.petlove.service.Interface.AdopterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdopterServiceImpl implements AdopterService {

    private final AdopterRepository repo;

    @Override
    public Page<AdopterDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }

    @Override
    public AdopterDto get(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Adopter " + id + " not found"));
    }

    @Override
    @Transactional
    public AdopterDto create(AdopterCreateDto dto) {
        Adopter a = Adopter.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .personalData(PersonalData.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .birthDate(dto.getBirthDate())
                        .docId(dto.getDocId())
                        .build())
                .build();
        return toDto(repo.save(a));
    }

    @Override
    @Transactional
    public AdopterDto update(Long id, AdopterCreateDto dto) {
        Adopter a = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adopter " + id + " not found"));
        a.setEmail(dto.getEmail());
        a.setPhone(dto.getPhone());
        a.getPersonalData().setFirstName(dto.getFirstName());
        a.getPersonalData().setLastName(dto.getLastName());
        a.getPersonalData().setBirthDate(dto.getBirthDate());
        a.getPersonalData().setDocId(dto.getDocId());
        return toDto(a);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Adopter " + id + " not found");
        repo.deleteById(id);
    }

    private AdopterDto toDto(Adopter a) {
        var pd = a.getPersonalData();
        return new AdopterDto(
                a.getId(),
                pd != null ? pd.getFirstName() : null,
                pd != null ? pd.getLastName() : null,
                pd != null ? pd.getDocId() : null,
                a.getEmail(),
                a.getPhone(),
                pd != null ? pd.getBirthDate() : null,
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
