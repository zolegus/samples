package com.zolegus.samples.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by zolegus on 28.08.2014.
 */
@Path("user")
public class UserResource {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    @GET
    @Path("print/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User produceJSON( @PathParam("name") String name ) {
        User user = new User(name, "Smith", 22, 1);
        return user;
    }

    @POST
    @Path("send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response consumeJSON(User user ) {
        String output = user.toString();
        return Response.status(200).entity(output).build();
    }

    private User user = new User();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User postUser( MultivaluedMap<String, String> personParams) {
        String firstName = personParams.getFirst("firstName");
        String lastName = personParams.getFirst("lastName");
        int age = Integer.valueOf(personParams.getFirst("age"));
        System.out.println("Storing posted " + firstName + " " + lastName + "  " + age);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        System.out.println("person info: " + user.getFirstName() + " " + user.getLastName() + " " + user.getAge());
        return user;

    }



}
