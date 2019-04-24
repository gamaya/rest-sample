package com.hoopladigital.service;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public class PetService {

	private final PetMapper petMapper;

	@Inject
	public PetService(final PetMapper petMapper) {
		this.petMapper = petMapper;
	}

	public List<Pet> getPetList(final Long personId) {
		return petMapper.getPetList(personId);
	}

	public Pet getPet(final Long personId, final Long id) {
		return petMapper.getPet(id, personId);
	}

	public void addPet(final Pet pet) {
		petMapper.insertPet(pet);
	}

	public void updatePet(final Pet pet) {
		petMapper.updatePet(pet);
	}

	public void deletePet(final Long personId, final Long id) {
		petMapper.deletePet(id, personId);
	}

	public void deleteAllPets(final Long personId) {
		petMapper.deleteAllPets(personId);
	}

}
