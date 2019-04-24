package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.service.PersonService;

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
import java.util.List;

@Path("/people")
public class PersonResource {

	private final PersonService personService;

	@Inject
	public PersonResource(final PersonService personService) {
		this.personService = personService;
	}

	@GET
	@Produces("application/json")
	public List<Person> getPersonList() {
		return personService.getPersonList();
	}

	@GET
	@Produces("application/json")
	@Path("{id}")
	public Response getPerson(@PathParam("id") final Long id) {

		final Person person = personService.getPerson(id);

		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok().entity(person).build();

	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response addPerson(final Person person) {

		// Add New Person
		personService.addPerson(person);

		// Return New Entity
		return Response
			.status(Response.Status.CREATED)
			.entity(person)
			.build();

	}

	@PUT
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatePerson(@PathParam("id") final Long id, final Person personRequest) {

		final Person person = personService.getPerson(id);

		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		person.setFirstName(personRequest.getFirstName());
		person.setMiddleName(personRequest.getMiddleName());
		person.setLastName(personRequest.getLastName());

		personService.updatePerson(person);

		return Response.ok().entity(person).build();

	}

	@DELETE
	@Path("{id}")
	public Response deletePerson(@PathParam("id") final Long id) {

		final Person person = personService.getPerson(id);

		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		personService.deletePerson(person.getId());

		return Response.noContent().build();

	}

}
