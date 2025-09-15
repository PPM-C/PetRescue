package com.petrescue.petlove.service.Imp;

import com.petrescue.petlove.dto.PetDto;
import com.petrescue.petlove.enums.EnergyLevel;
import com.petrescue.petlove.enums.PetStatus;
import com.petrescue.petlove.enums.Size;
import com.petrescue.petlove.enums.Species;
import com.petrescue.petlove.model.pet.Pet;
import com.petrescue.petlove.repository.PetRepository;
import com.petrescue.petlove.service.Interface.PetMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PetMatchServiceImpl implements PetMatchService {

    private final PetRepository petRepo;

    @Override
    public PetDto suggestRandom(Species species, EnergyLevel energy, Size size) {
        Specification<Pet> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), PetStatus.Adoptable));
            if (species != null) predicates.add(cb.equal(root.get("species"), species));
            if (energy != null)  predicates.add(cb.equal(root.get("energyLevel"), energy));
            if (size != null)    predicates.add(cb.equal(root.get("size"), size));
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        long total = petRepo.count(spec);
        if (total == 0) throw new IllegalArgumentException("No hay mascotas que cumplan los criterios");

        int offset = (int) ThreadLocalRandom.current().nextLong(total);
        Pageable page = PageRequest.of(offset, 1);

        Pet pet = petRepo.findAll(spec, page).stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No hay mascotas disponibles"));

        return toDto(pet);
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
