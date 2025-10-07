package com.petrescue.petlove.service.impl;

import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.enums.EnergyLevel;
import com.petrescue.petlove.enums.PetStatus;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.enums.Species;
import com.petrescue.petlove.model.pet.Pet;
import com.petrescue.petlove.repository.PetRepository;
import com.petrescue.petlove.service.interfaces.PetMatchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PetMatchServiceImpl implements PetMatchService {

    private final PetRepository petRepo;

    public Page<PetDto> match(String q, Pageable pageable) {
        Page<Pet> page = (q == null || q.isBlank())
                ? petRepo.findAll(pageable)
                : petRepo.findByNameContainingIgnoreCase(q, pageable);
        return page.map(this::toDto);
    }

    @Override
    public PetDto suggestRandom(Species species, EnergyLevel energy, Size size) {
        List<Pet> candidates = petRepo.findAll().stream()
                .filter(p -> species == null || p.getSpecies() == species)
                .filter(p -> energy == null || energy.equals(p.getEnergyLevel()))
                .filter(p -> size == null || size.equals(p.getSize()))
                .filter(p -> p.getStatus() == PetStatus.AVAILABLE)
                .toList();

        if (candidates.isEmpty()) {
            throw new EntityNotFoundException("No hay mascotas que cumplan los criterios.");
        }

        Pet pick = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
        return toDto(pick);
    }

// Helpers para convertir entre Instant y LocalDateTime
private static LocalDateTime toLocalDateTime(Instant i) {
    return i == null ? null : LocalDateTime.ofInstant(i, ZoneId.systemDefault());
}
private static Instant toInstant(LocalDateTime dt) {
    return dt == null ? null : dt.atZone(ZoneId.systemDefault()).toInstant();
}

private EnergyLevel toEnum(Integer code) {
    if (code == null) return null;
    return EnergyLevel.fromCode(code);
}

private Integer toCode(EnergyLevel level) {
    return level == null ? null : level.ordinal();
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
                p.getNotes(),
                p.getPhotoUrl()
        );
    }
}
