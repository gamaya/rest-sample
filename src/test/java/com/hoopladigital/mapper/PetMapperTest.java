package com.hoopladigital.mapper;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.test.AbstractMapperTest;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public class PetMapperTest extends AbstractMapperTest {

	@Inject
	private PetMapper petMapper;

	@Test
	public void should_get_pet_list_for_person() throws Exception {

		// setup
		final Pet samson = new Pet();
		samson.setId(1L);
		samson.setPersonId(1L);
		samson.setName("Samson");

		// run test
		final List<Pet> petList = petMapper.getPetList(1L);

		// verify mocks / capture values

		// assert results
		assertEquals(5, petList.size());
		beanTestHelper.diffBeans(samson, petList.get(0));

	}

	@Test
	public void should_get_pet_for_person() throws Exception {

		// Setup
		final Pet expectedSamson = new Pet();
		expectedSamson.setId(1L);
		expectedSamson.setPersonId(1L);
		expectedSamson.setName("Samson");

		// run tests
		final Pet actualSamson = petMapper.getPet(1L, 1L);

		// assert results
		assertNotNull(actualSamson);
		beanTestHelper.diffBeans(expectedSamson, actualSamson);


	}

	@Test
	public void should_create_pet_for_person() throws Exception {

		// Setup
		Pet juno = new Pet();
		juno.setPersonId(2L);
		juno.setName("Juno");

		// Run Tests
		petMapper.insertPet(juno);
		List<Pet> petList = petMapper.getPetList(2L);

		// assert results
		assertEquals(1, petList.size());
		beanTestHelper.diffBeans(juno, petList.get(0));

	}

	@Test
	public void should_update_pet_for_person() throws Exception {

		// Setup
		Pet samson = petMapper.getPet(1L, 1L);
		samson.setName("Sammy");

		// Run Tests
		petMapper.updatePet(samson);
		Pet johnny = petMapper.getPet(1L,1L);

		// Assert Results
		beanTestHelper.diffBeans(samson, johnny);

	}

	@Test
	public void should_delete_pet_for_person()  {


		// Run Tests
		petMapper.deletePet(3L, 1L); // Leonidas
		Pet pet = petMapper.getPet(3L, 1L);
		List<Pet> petList = petMapper.getPetList(1L);

		// Assert Results
		assertNull(pet);
		assertEquals(4, petList.size());


	}

	@Test
	public void should_delete_all_pets_for_person()  {


		// Run Tests
		petMapper.deleteAllPets(1L);
		List<Pet> petList = petMapper.getPetList(1L);

		// Assert Results
		assertEquals(0, petList.size());


	}

}
