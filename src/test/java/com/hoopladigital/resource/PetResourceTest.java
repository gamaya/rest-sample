package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;
import com.hoopladigital.test.AbstractTest;
import com.hoopladigital.ws.PersonPetResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public class PetResourceTest extends AbstractTest {

	@Mock
	private PersonService personService;

	@Mock
	private PetService petService;

	private PetResource petResource;

	private Person person;

	@Before
	public void beforePersonResourceTest() {

		petResource = new PetResource(personService, petService);

		person = new Person();
		person.setId(100L);
		person.setFirstName("Alan");
		person.setLastName("Turing");

	}

	@Test
	public void should_get_pet_list_for_person() throws Exception {

		// setup
		final List<Pet> petList = Collections.emptyList();
		PersonPetResponse expected = PersonPetResponse.builder().withPerson(person).withPets(petList).build();
		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPetList(100L)).thenReturn(petList);

		// run test
		final Response response = petResource.getPets(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPetList(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(200, response.getStatus());
		beanTestHelper.diffBeans(expected, response.getEntity());

	}

	@Test
	public void should_get_pet_list_for_person_not_found()  {

		// setup
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = petResource.getPets(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_get_pet_for_person() throws Exception {

		// setup
		final Pet pet = new Pet();
		pet.setId(1L);
		pet.setPersonId(100L);
		pet.setName("Spot");

		PersonPetResponse expected = PersonPetResponse.builder().withPerson(person).withPets(Collections.singletonList(pet)).build();

		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPet(100L, 1L)).thenReturn(pet);

		// run test
		final Response response = petResource.getPet(100L, 1L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPet(100L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(200, response.getStatus());
		beanTestHelper.diffBeans(expected, response.getEntity());

	}

	@Test
	public void should_get_pet_for_person_not_found() {

		// setup
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = petResource.getPet(100L, 1L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_get_pet_for_person_and_pet_not_found() throws Exception {

		// setup
		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPet(100L, 1L)).thenReturn(null);

		// run test
		final Response response = petResource.getPet(100L, 1L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPet(100L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_create_pet() throws Exception {

		// setup
		Pet hansel = new Pet();
		hansel.setId(1L);
		hansel.setName("Hansel");
		hansel.setPersonId(100L);

		PersonPetResponse personPetResponse = PersonPetResponse.builder()
												.withPerson(person).withPets(Collections.singletonList(hansel)).build();

		when(personService.getPerson(100L)).thenReturn(person);

		// run test
		final Response response = petResource.addPet(100L, hansel);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).addPet(any(Pet.class));
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(personPetResponse, response.getEntity());
		assertEquals(201, response.getStatus());

	}

	@Test
	public void should_create_pet_person_not_found() throws Exception {

		// setup
		Pet hansel = new Pet();
		hansel.setId(1L);
		hansel.setName("Hansel");
		hansel.setPersonId(100L);

		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = petResource.addPet(100L, hansel);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_update_pet() throws Exception {

		final Pet spot = new Pet();
		spot.setId(200L);
		spot.setPersonId(100L);
		spot.setName("Spot");

		final Pet hansel = new Pet();
		hansel.setId(200L);
		hansel.setPersonId(100L);
		hansel.setName("Hansel");

		PersonPetResponse personPetResponse = PersonPetResponse.builder()
												.withPerson(person)
												.withPets(Collections.singletonList(hansel))
												.build();

		ArgumentCaptor<Pet> petArgumentCaptor = ArgumentCaptor.forClass(Pet.class);
		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPet(100L, 200L)).thenReturn(spot);

		// run test
		final Response response = petResource.updatePet(100L, 200L, hansel);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPet(100L, 200L);
		verify(petService).updatePet(petArgumentCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(personPetResponse.getPerson(), ((PersonPetResponse)response.getEntity()).getPerson());
		beanTestHelper.diffBeans(personPetResponse.getPets(), ((PersonPetResponse)response.getEntity()).getPets());
		assertEquals(200, response.getStatus());

	}

	@Test
	public void should_update_pet_person_not_found() {

		final Pet spot = new Pet();
		spot.setId(200L);
		spot.setPersonId(100L);
		spot.setName("Spot");

		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = petResource.updatePet(100L, 200L, spot);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_update_pet_pet_not_found() throws Exception {

		final Pet hansel = new Pet();
		hansel.setId(200L);
		hansel.setPersonId(100L);
		hansel.setName("Hansel");

		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPet(100L, 200L)).thenReturn(null);

		// run test
		final Response response = petResource.updatePet(100L, 200L, hansel);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPet(100L, 200L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_delete_pet() {

		// setup
		final Pet hansel = new Pet();
		hansel.setId(1L);
		hansel.setPersonId(100L);
		hansel.setName("Hansel");

		ArgumentCaptor<Long> personArgumentCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Long> petArgumentCaptor = ArgumentCaptor.forClass(Long.class);
		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPet(100L, 1L)).thenReturn(hansel);

		// run test
		final Response response = petResource.deletePet(100L, 1L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPet(100L, 1L);
		verify(petService).deletePet(personArgumentCaptor.capture(), petArgumentCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(person.getId(), personArgumentCaptor.getValue());
		assertEquals(hansel.getId(), petArgumentCaptor.getValue());
		assertEquals(204, response.getStatus());
	}

	@Test
	public void should_delete_pet_person_not_found() {

		// setup
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = petResource.deletePet(100L, 1L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());
	}

	@Test
	public void should_delete_pet_not_found() {

		// setup
		when(personService.getPerson(100L)).thenReturn(person);
		when(petService.getPet(100L, 1L)).thenReturn(null);

		// run test
		final Response response = petResource.deletePet(100L, 1L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).getPet(100L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());
	}

	@Test
	public void should_delete_all_pets() {

		// setup
		ArgumentCaptor<Long> personArgumentCaptor = ArgumentCaptor.forClass(Long.class);
		when(personService.getPerson(100L)).thenReturn(person);

		// run test
		final Response response = petResource.deleteAllPets(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(petService).deleteAllPets(personArgumentCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(person.getId(), personArgumentCaptor.getValue());
		assertEquals(204, response.getStatus());
	}

	@Test
	public void should_delete_all_pets_person_not_found() {

		// setup
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = petResource.deleteAllPets(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());
	}

}
