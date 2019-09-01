package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;
import com.hoopladigital.ws.PersonPetResponse;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Path("/people/{personId}/pets")
public class PetResource {

	private final PersonService personService;
	private final PetService petService;

	@Inject
	public PetResource(final PersonService personService, final PetService petService) {
		this.personService = personService;
		this.petService = petService;
	}

	@GET
	@Produces("application/json")
	public Response getPets(@PathParam("personId") final Long personId) {

		// Get Person - A small change
		final Person person = personService.getPerson(personId);
		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Get list of Pets for Person
		final List<Pet> pets = petService.getPetList(personId);

		// Build Response
		return Response.ok()
			.entity(PersonPetResponse.builder()
				.withPerson(person)
				.withPets(pets).build())
			.build();
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getPet(@PathParam("personId") final Long personId, @PathParam("id") final Long id) {

		// Get Person
		final Person person = personService.getPerson(personId);
		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Get Pet
		final Pet pet = petService.getPet(personId, id);
		if (pet == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Build Response
		return Response.ok()
			.entity(PersonPetResponse.builder()
				.withPerson(person)
				.withPets(Collections.singletonList(pet)).build())
			.build();

	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPet(@PathParam("personId") final Long personId, final Pet pet) {

		// Get Person
		final Person person = personService.getPerson(personId);

		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Create Pet Object
		final Pet newPet = new Pet();
		newPet.setPersonId(personId);
		newPet.setName(pet.getName());

		petService.addPet(newPet);

		// Build Response
		return Response.status(Response.Status.CREATED)
			.entity(PersonPetResponse.builder()
				.withPerson(person)
				.withPets(Collections.singletonList(pet)).build())
			.build();

	}

	@PUT
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatePet(@PathParam("personId") final Long personId, @PathParam("id") final Long id, final Pet petRequest) {

		// Get Person
		final Person person = personService.getPerson(personId);
		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Get Pet
		final Pet pet = petService.getPet(personId, id);
		if (pet == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Update Pet
		pet.setName(petRequest.getName());

		petService.updatePet(pet);

		// Build Response
		return Response.ok()
			.entity(PersonPetResponse.builder()
				.withPerson(person)
				.withPets(Collections.singletonList(pet)).build())
			.build();

	}

	@DELETE
	@Path("{id}")
	public Response deletePet(@PathParam("personId") final Long personId, @PathParam("id") final Long id) {

		// Get Person
		final Person person = personService.getPerson(personId);
		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Get Pet
		final Pet pet = petService.getPet(personId, id);
		if (pet == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Delete Pet
		petService.deletePet(personId, id);

		return Response.noContent().build();

	}

	@DELETE
	public Response deleteAllPets(@PathParam("personId") final Long personId) {

		// Get Person
		final Person person = personService.getPerson(personId);
		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Delete Pet
		petService.deleteAllPets(personId);

		return Response.noContent().build();

	}

}
