package com.petrescue.petlove.service.impl;

import com.petrescue.petlove.dto.PetCreateDto;
import com.petrescue.petlove.dto.PetDto;          
import com.petrescue.petlove.dto.PetUpdateDto;
import com.petrescue.petlove.enums.*;
import com.petrescue.petlove.model.pet.Pet;
import com.petrescue.petlove.repository.PetRepository;
import com.petrescue.petlove.repository.ShelterRepository;
import com.petrescue.petlove.service.Interface.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Instant; 
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ShelterRepository shelterRepository;

    @SuppressWarnings("unchecked")
    private static <T, R> R prop(T obj, String name, Class<R> type) {
        if (obj == null) return null;
        String getter1 = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        String getter2 = name;
        try {
            Method m;
            try { m = obj.getClass().getMethod(getter1); }
            catch (NoSuchMethodException ex) { m = obj.getClass().getMethod(getter2); }
            Object val = m.invoke(obj);
            return val == null ? null : (R) val;
        } catch (Exception e) {
            return null;
        }
    }

    private static EnergyLevel toEnum(Integer code) { return EnergyLevel.fromCode(code); }
    private static Integer   toCode(EnergyLevel e)  { return e == null ? null : e.getCode(); }

    // === mapping entidad -> record PetDto ===
    private static PetDto toDto(Pet p) {
        return new PetDto(
                p.getId(),
                p.getSpecies(),                  
                p.getName(),
                p.getSex(),                       
                p.getSize(),                     
                p.getStatus(),                   
                p.getTemperament(),              
                toEnum(p.getEnergyLevel()),       
                p.getGoodWithDogs(),
                p.getGoodWithCats(),
                p.getNeutered(),
                p.getArrivalDate(),
                p.getDepartureDate(),
                p.getShelter() == null ? null : p.getShelter().getId(),
                p.getPhotoUrl(),
                p.getNotes()
        );
    }

    private Pet fromCreateDto(PetCreateDto dto) {
        Pet p = new Pet();
        p.setSpecies(prop(dto, "species", Species.class));
        p.setName(prop(dto, "name", String.class));
        p.setSex(prop(dto, "sex", Sex.class));
        p.setSize(prop(dto, "size", Size.class));
        p.setStatus(prop(dto, "status", PetStatus.class));
        p.setTemperament(prop(dto, "temperament", Temperament.class));
        p.setEnergyLevel(toCode(prop(dto, "energyLevel", EnergyLevel.class)));
        p.setGoodWithDogs(prop(dto, "goodWithDogs", Boolean.class));
        p.setGoodWithCats(prop(dto, "goodWithCats", Boolean.class));
        p.setNeutered(prop(dto, "neutered", Boolean.class));
        p.setArrivalDate(prop(dto, "arrivalDate", LocalDate.class));
        p.setDepartureDate(prop(dto, "departureDate", LocalDate.class));
        p.setNotes(prop(dto, "notes", String.class));
        p.setPhotoUrl(prop(dto, "photoUrl", String.class));

        Long shelterId = prop(dto, "shelterId", Long.class);
        if (shelterId != null) {
            p.setShelter(shelterRepository.findById(shelterId)
                    .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterId)));
        }
        return p;
    }

    private void applyUpdate(Pet p, PetUpdateDto dto) {
        Optional.ofNullable(prop(dto, "species", Species.class)).ifPresent(p::setSpecies);
        Optional.ofNullable(prop(dto, "name", String.class)).ifPresent(p::setName);
        Optional.ofNullable(prop(dto, "sex", Sex.class)).ifPresent(p::setSex);
        Optional.ofNullable(prop(dto, "size", Size.class)).ifPresent(p::setSize);
        Optional.ofNullable(prop(dto, "status", PetStatus.class)).ifPresent(p::setStatus);
        Optional.ofNullable(prop(dto, "temperament", Temperament.class)).ifPresent(p::setTemperament);
        Optional.ofNullable(prop(dto, "energyLevel", EnergyLevel.class)).ifPresent(e -> p.setEnergyLevel(toCode(e)));
        Optional.ofNullable(prop(dto, "goodWithDogs", Boolean.class)).ifPresent(p::setGoodWithDogs);
        Optional.ofNullable(prop(dto, "goodWithCats", Boolean.class)).ifPresent(p::setGoodWithCats);
        Optional.ofNullable(prop(dto, "neutered", Boolean.class)).ifPresent(p::setNeutered);
        Optional.ofNullable(prop(dto, "arrivalDate", LocalDate.class)).ifPresent(p::setArrivalDate);
        Optional.ofNullable(prop(dto, "departureDate", LocalDate.class)).ifPresent(p::setDepartureDate);
        Optional.ofNullable(prop(dto, "notes", String.class)).ifPresent(p::setNotes);
        Optional.ofNullable(prop(dto, "photoUrl", String.class)).ifPresent(p::setPhotoUrl);
        Long shelterId = prop(dto, "shelterId", Long.class);
        if (shelterId != null) {
            p.setShelter(shelterRepository.findById(shelterId)
                    .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterId)));
        }
    }

    @Override
    public Page<PetDto> list(String q, Pageable pageable) {
        return petRepository.findAll(pageable).map(PetServiceImpl::toDto);
    }

    @Override
    public PetDto findById(Long id) {
        Pet p = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));
        return toDto(p);
    }

    @Override
    public PetDto create(PetCreateDto dto) {
        Pet p = fromCreateDto(dto);
        p = petRepository.save(p);
        return toDto(p);
    }

    @Override
    public PetDto update(Long id, PetUpdateDto dto) {
        Pet p = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));
        applyUpdate(p, dto);
        p = petRepository.save(p);
        return toDto(p);
    }

    @Override
    public void delete(long id) {
        petRepository.deleteById(id);
    }
}
