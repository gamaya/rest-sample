package com.hoopladigital.ws;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;

import java.util.List;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public final class PersonPetResponse {

	private final Person person;
	private final List<Pet> pets;


	private PersonPetResponse(final Builder builder) {
		this.person = builder.person;
		this.pets = builder.pets;
	}

	public Person getPerson() {
		return person;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public static PersonPetResponse.Builder builder() {
		return new PersonPetResponse.Builder();
	}

	public static class Builder {

		private Person person;
		private List<Pet> pets;

		public Builder withPerson(final Person person) {
			this.person = person;
			return this;
		}

		public Builder withPets(final List<Pet> pets) {
			this.pets = pets;
			return this;
		}

		public PersonPetResponse build() {
			return new PersonPetResponse(this);
		}
	}

}
