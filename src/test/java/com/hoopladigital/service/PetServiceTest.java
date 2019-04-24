package com.hoopladigital.service;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;
import com.hoopladigital.test.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public class PetServiceTest extends AbstractTest {

	@Mock
	private PetMapper petMapper;
	private PetService petService;

	@Before
	public void beforePersonServiceTest() {
		petService = new PetService(petMapper);
	}

	@Test
	public void should_get_pet_list() {

		// setup
		final List<Pet> expected = Collections.emptyList();
		when(petMapper.getPetList(1L)).thenReturn(expected);

		// run test
		final List<Pet> actual = petService.getPetList(1L);

		// verify mocks / capture values
		verify(petMapper).getPetList(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_pet() {

		// setup
		final Pet spot = new Pet();
		spot.setId(1L);
		spot.setPersonId(10L);
		spot.setName("Spot");

		when(petMapper.getPet(1L, 10L)).thenReturn(spot);

		// Run Test
		final Pet pet = petService.getPet(10L, 1L);

		// Verify Mocks / capture values
		verify(petMapper).getPet(1L, 10L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(spot, pet);

	}

	@Test
	public void should_add_pet() throws Exception{

		// setup
		final Pet hansel = new Pet();
		hansel.setId(1L);
		hansel.setPersonId(100L);
		hansel.setName("Hansel");

		ArgumentCaptor<Pet> captor = ArgumentCaptor.forClass(Pet.class);

		// run tests
		petService.addPet(hansel);

		// Verify Mocks / capture values
		verify(petMapper).insertPet(captor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));
		Pet pet = captor.getValue();

		// assert results
		beanTestHelper.diffBeans(hansel, pet);

	}

	@Test
	public void should_update_pet() throws Exception {

		// setup
		final Pet gretel = new Pet();
		gretel.setId(1L);
		gretel.setPersonId(100L);
		gretel.setName("Gretel");

		ArgumentCaptor<Pet> captor = ArgumentCaptor.forClass(Pet.class);

		// run tests
		petService.updatePet(gretel);

		// Verify Mocks / capture values
		verify(petMapper).updatePet(captor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));
		Pet pet = captor.getValue();

		// assert results
		beanTestHelper.diffBeans(gretel, pet);

	}

	@Test
	public void should_delete_pet() {

		// setup
		ArgumentCaptor<Long> personIdCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Long> petIdCaptor = ArgumentCaptor.forClass(Long.class);

		// run tests
		petService.deletePet(1L, 10L);

		// Verify Mocks / capture values
		verify(petMapper).deletePet(petIdCaptor.capture(), personIdCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(Long.valueOf(1L), personIdCaptor.getValue());
		assertEquals(Long.valueOf(10L), petIdCaptor.getValue());


	}

	@Test
	public void should_delete_all_pets() {

		// setup
		ArgumentCaptor<Long> personIdCaptor = ArgumentCaptor.forClass(Long.class);

		// run tests
		petService.deleteAllPets(1L);

		// Verify Mocks / capture values
		verify(petMapper).deleteAllPets(personIdCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));
		Long deletedId = personIdCaptor.getValue();

		// assert results
		assertEquals(Long.valueOf(1L), deletedId);

	}
}
