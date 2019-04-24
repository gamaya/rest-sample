package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;
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

public class PersonServiceTest extends AbstractTest {

	@Mock
	private PersonMapper personMapper;
	private PersonService personService;

	@Before
	public void beforePersonServiceTest() {
		personService = new PersonService(personMapper);
	}

	@Test
	public void should_get_person_list() {

		// setup
		final List<Person> expected = Collections.emptyList();
		when(personMapper.getPersonList()).thenReturn(expected);

		// run test
		final List<Person> actual = personService.getPersonList();

		// verify mocks / capture values
		verify(personMapper).getPersonList();
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_person() {

		// setup
		final Person isaacNewton = new Person();
		isaacNewton.setId(1L);
		isaacNewton.setFirstName("Isaac");
		isaacNewton.setMiddleName("M");
		isaacNewton.setLastName("Newton");

		when(personMapper.getPerson(1L)).thenReturn(isaacNewton);

		// Run Test
		final Person person = personService.getPerson(1L);

		// Verify Mocks / capture values
		verify(personMapper).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(isaacNewton, person);

	}

	@Test
	public void should_add_person() throws Exception{

		// setup
		final Person alanTuring = new Person();
		alanTuring.setId(1L);
		alanTuring.setFirstName("Alan");
		alanTuring.setMiddleName("Mathison");
		alanTuring.setLastName("Turing");

		ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

		// run tests
		personService.addPerson(alanTuring);

		// Verify Mocks / capture values
		verify(personMapper).insertPerson(captor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));
		Person person = captor.getValue();

		// assert results
		beanTestHelper.diffBeans(alanTuring, person);

	}

	@Test
	public void should_update_person() throws Exception {

		// setup
		final Person alanTuring = new Person();
		alanTuring.setId(1L);
		alanTuring.setFirstName("Alan");
		alanTuring.setMiddleName("Mathison");
		alanTuring.setLastName("Turing");

		ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

		// run tests
		personService.updatePerson(alanTuring);

		// Verify Mocks / capture values
		verify(personMapper).updatePerson(captor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));
		Person person = captor.getValue();

		// assert results
		beanTestHelper.diffBeans(alanTuring, person);

	}

	@Test
	public void should_delete_person() {

		// setup
		ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

		// run tests
		personService.deletePerson(1L);

		// Verify Mocks / capture values
		verify(personMapper).deletePerson(captor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));
		Long deletedId = captor.getValue();

		// assert results
		assertEquals(Long.valueOf(1L), deletedId);

	}

}
