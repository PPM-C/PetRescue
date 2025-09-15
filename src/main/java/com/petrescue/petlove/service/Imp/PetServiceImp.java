package com.petrescue.petlove.service.Imp;

import com.petrescue.petlove.dto.PetCreateDto;
import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.dto.PetUpdateDto;
import com.petrescue.petlove.enums.PetStatus;
import com.petrescue.petlove.model.pet.Cat;
import com.petrescue.petlove.model.pet.Dog;
import com.petrescue.petlove.model.pet.Ferret;
import com.petrescue.petlove.model.pet.Pet;
import com.petrescue.petlove.model.shelter.Shelter;
import com.petrescue.petlove.repository.PetRepository;
import com.petrescue.petlove.repository.ShelterRepository;
import com.petrescue.petlove.service.Interface.PetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetServiceImp implements PetService {

    private final PetRepository petRepo;
    private final ShelterRepository shelterRepo;

    @Override
    public Page<PetDto> List(Pageable pageable) {
        return petRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    public PetDto get(long id) {
        return petRepo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Pet " + id + " not found"));
    }

    @Override
    @Transactional
    public PetDto create(PetCreateDto dto) {
        Shelter shelter = shelterRepo.findById(dto.shelterId())
                .orElseThrow(() -> new EntityNotFoundException("Shelter " + dto.shelterId() + " not found"));

        Pet pet = switch (dto.species()) {
            case Dog -> Dog.builder()
                    .breed(dto.breed())
                    .isTrained(Boolean.TRUE.equals(dto.isTrained()))
                    .build();
            case Cat -> Cat.builder()
                    .breed(dto.breed())
                    .litterTrained(dto.litterTrained() == null ? true : dto.litterTrained())
                    .build();
            case Ferret -> Ferret.builder()
                    .breed(dto.breed())
                    .odorControlTrained(Boolean.TRUE.equals(dto.odorControlTrained()))
                    .build();
        };

        pet.setShelter(shelter);                  // ← asignar shelter
        pet.setSpecies(dto.species());
        pet.setName(dto.name());
        pet.setSex(dto.sex());
        pet.setSize(dto.size());
        pet.setArrivalDate(dto.arrivalDate());
        pet.setTemperament(dto.temperament());
        pet.setEnergyLevel(dto.energyLevel());
        pet.setGoodWithDogs(dto.goodWithDogs() == null ? true : dto.goodWithDogs());
        pet.setGoodWithCats(dto.goodWithCats() == null ? true : dto.goodWithCats());
        pet.setNeutered(dto.neutered() == null ? false : dto.neutered());
        pet.setStatus(PetStatus.Adoptable);
        return toDto(petRepo.save(pet));
    }

    @Override
    @Transactional
    public PetDto update(long id, PetUpdateDto dto) { // ← firma con 'long' como la interfaz
        Pet pet = petRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet " + id + " not found"));

        if (dto.name() != null)         pet.setName(dto.name());
        if (dto.sex() != null)          pet.setSex(dto.sex());
        if (dto.size() != null)         pet.setSize(dto.size());
        if (dto.temperament() != null)  pet.setTemperament(dto.temperament());
        if (dto.energyLevel() != null)  pet.setEnergyLevel(dto.energyLevel());
        if (dto.goodWithDogs() != null) pet.setGoodWithDogs(dto.goodWithDogs());
        if (dto.goodWithCats() != null) pet.setGoodWithCats(dto.goodWithCats());
        if (dto.neutered() != null)     pet.setNeutered(dto.neutered());
        if (dto.arrivalDate() != null)  pet.setArrivalDate(dto.arrivalDate());
        if (dto.notes() != null)        pet.setNotes(dto.notes());

        return toDto(pet);
    }

    @Override
    @Transactional
    public void delete(long id) { // ← firma con 'long' como la interfaz
        if (!petRepo.existsById(id)) throw new EntityNotFoundException("Pet " + id + " not found");
        petRepo.deleteById(id);
    }

    private PetDto toDto(Pet p) {
        return new PetDto(
                p.getId(),
                p.getSpecies(),
                p.getName(),
                p.getSex(),
                p.getSize(),
                p.getStatus(),
                p.getTemperament(),
                p.getEnergyLevel(),
                p.getGoodWithDogs(),
                p.getGoodWithCats(),
                p.getNeutered(),
                p.getArrivalDate(),
                p.getDepartureDate(),
                p.getShelter() != null ? p.getShelter().getId() : null,
                p.getNotes()
        );
    }
}
