package com.hoopladigital.mapper;

import com.hoopladigital.bean.Pet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public interface PetMapper {
	List<Pet> getPetList(Long personId);
	Pet getPet(@Param("id") Long id, @Param("personId") Long personId);
	void insertPet(Pet pet);
	void updatePet(Pet pet);
	void deletePet(@Param("id") Long id, @Param("personId") Long personId);
	void deleteAllPets(Long personId);
}
