package com.hoopladigital.mapper;

import com.hoopladigital.bean.Person;
import com.hoopladigital.test.AbstractMapperTest;
import org.junit.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.*;

public class PersonMapperTest extends AbstractMapperTest {

	@Inject
	private PersonMapper personMapper;

	@Test
	public void should_get_person_list() throws Exception {

		// setup
		final Person george = new Person();
		george.setId(1L);
		george.setFirstName("George");
		george.setLastName("Washington");

		// run test
		final List<Person> personList = personMapper.getPersonList();

		// verify mocks / capture values

		// assert results
		assertEquals(10, personList.size());
		beanTestHelper.diffBeans(george, personList.get(0));

	}

	@Test
	public void should_get_person() throws Exception {

		// Setup
		final Person expectedGeorge = new Person();
		expectedGeorge.setId(1L);
		expectedGeorge.setFirstName("George");
		expectedGeorge.setLastName("Washington");

		// run tests
		final Person actualGeorge = personMapper.getPerson(1L);

		// assert results
		assertNotNull(actualGeorge);
		beanTestHelper.diffBeans(expectedGeorge, actualGeorge);


	}

	@Test
	public void should_create_person() throws Exception {

		// Setup
		Person riemann = new Person();
		riemann.setFirstName("Georg");
		riemann.setMiddleName("Friedrich Bernhard");
		riemann.setLastName("Riemann");

		// Run Tests
		personMapper.insertPerson(riemann);
		List<Person> personList = personMapper.getPersonList();

		// assert results
		assertEquals(11, personList.size());
		beanTestHelper.diffBeans(riemann, personList.get(10));

	}

	@Test
	public void should_update_person() throws Exception {

		// Setup
		Person johnAdams = personMapper.getPerson(2L);
		johnAdams.setFirstName("Johnny");
		johnAdams.setMiddleName("P");
		johnAdams.setLastName("Adamson");

		// Run Tests
		personMapper.updatePerson(johnAdams);
		Person johnny = personMapper.getPerson(2L);

		// Assert Results
		beanTestHelper.diffBeans(johnAdams, johnny);

	}

	@Test
	public void should_delete_person() {


		// Run Tests
		personMapper.deletePerson(5L); // James Monroe
		Person person = personMapper.getPerson(5L);
		List<Person> peopleList = personMapper.getPersonList();

		// Assert Results
		assertNull(person);
		assertEquals(9, peopleList.size());

	}

}
