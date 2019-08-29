package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;

import javax.inject.Inject;
import java.util.List;

public class PersonService {

	private final PersonMapper personMapper;

	@Inject
	public PersonService(final PersonMapper personMapper) {
		this.personMapper = personMapper;
	}

	public List<Person> getPersonList() {
		return personMapper.getPersonList();
	}

	public Person getPerson(final Long id) {
		return personMapper.getPerson(id);
	}

	public void addPerson(final Person person) {
		personMapper.insertPerson(person);
	}

	public void updatePerson(final Person person) {
		personMapper.updatePerson(person);
	}

	public void deletePerson(final Long id) {
		personMapper.deletePerson(id);
	}

}
