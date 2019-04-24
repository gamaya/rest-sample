package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.test.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PersonResourceTest extends AbstractTest {

	@Mock
	private PersonService personService;

	private PersonResource personResource;

	@Before
	public void beforePersonResourceTest() {
		personResource = new PersonResource(personService);
	}

	@Test
	public void should_get_person_list() {

		// setup
		final List<Person> expected = Collections.emptyList();
		when(personService.getPersonList()).thenReturn(expected);

		// run test
		final List<Person> actual = personResource.getPersonList();

		// verify mocks / capture values
		verify(personService).getPersonList();
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_person() {

		// setup
		final Person expected = new Person();
		expected.setId(100L);
		expected.setFirstName("Alan");
		expected.setMiddleName("Mathison");
		expected.setLastName("Turing");

		when(personService.getPerson(100L)).thenReturn(expected);

		// run test
		final Response response = personResource.getPerson(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, response.getEntity());
		assertEquals(200, response.getStatus());

	}

	@Test
	public void should_get_person_not_found() {

		// setup
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = personResource.getPerson(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_create_person() {

		// setup
		final Person alanTuring = new Person();
		alanTuring.setId(100L);
		alanTuring.setFirstName("Alan");
		alanTuring.setMiddleName("Mathison");
		alanTuring.setLastName("Turing");

		// run test
		final Response response = personResource.addPerson(alanTuring);

		// verify mocks / capture values
		verify(personService).addPerson(alanTuring);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(alanTuring, response.getEntity());
		assertEquals(201, response.getStatus());

	}

	@Test
	public void should_update_person() throws Exception {

		// setup
		final Person alanTuring = new Person();
		alanTuring.setId(100L);
		alanTuring.setFirstName("Alan");
		alanTuring.setMiddleName("Mathison");
		alanTuring.setLastName("Turing");

		final Person bernhardRiemann = new Person();
		bernhardRiemann.setId(100L);
		bernhardRiemann.setFirstName("Bernhard");
		bernhardRiemann.setMiddleName("Georg");
		bernhardRiemann.setLastName("Riemann");

		ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
		when(personService.getPerson(100L)).thenReturn(alanTuring);

		// run test
		final Response response = personResource.updatePerson(100L, bernhardRiemann);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(personService).updatePerson(personArgumentCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(bernhardRiemann, response.getEntity());
		assertEquals(200, response.getStatus());

	}

	@Test
	public void should_update_person_not_found() {

		// setup
		final Person bernhardRiemann = new Person();
		bernhardRiemann.setId(100L);
		bernhardRiemann.setFirstName("Bernhard");
		bernhardRiemann.setMiddleName("Georg");
		bernhardRiemann.setLastName("Riemann");
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = personResource.updatePerson(100L, bernhardRiemann);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_delete_person() {

		// setup
		final Person alanTuring = new Person();
		alanTuring.setId(100L);
		alanTuring.setFirstName("Alan");
		alanTuring.setMiddleName("Mathison");
		alanTuring.setLastName("Turing");

		ArgumentCaptor<Long> personArgumentCaptor = ArgumentCaptor.forClass(Long.class);
		when(personService.getPerson(100L)).thenReturn(alanTuring);

		// run test
		final Response response = personResource.deletePerson(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verify(personService).deletePerson(personArgumentCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(alanTuring.getId(), personArgumentCaptor.getValue());
		assertEquals(204, response.getStatus());

	}

	@Test
	public void should_delete_person_not_found() {

		// setup
		when(personService.getPerson(100L)).thenReturn(null);

		// run test
		final Response response = personResource.deletePerson(100L);

		// verify mocks / capture values
		verify(personService).getPerson(100L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(404, response.getStatus());

	}

}
