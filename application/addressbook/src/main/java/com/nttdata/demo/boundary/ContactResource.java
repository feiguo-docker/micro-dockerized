package com.nttdata.demo.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.nttdata.demo.control.ContactCrudController;
import com.nttdata.demo.entity.Contact;

@Path("contacts")
@RequestScoped
public class ContactResource {

  @Inject
  private ContactCrudController contactCrudController;

  @POST
  public Response create(final Contact contact, @Context UriInfo uriInfo) {
    final Contact created = contactCrudController.create(contact);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder();
    builder.path(Long.toString(created.getId()));
    return Response.created(builder.build()).entity(created).build();
  }

  @GET
  @Path("{id}")
  public Response read(@PathParam("id") final Long id) {
    final Contact contact = contactCrudController.read(id);
    return Response.ok(contact).build();
  }

  @PUT
  @Path("{id}")
  public Response update(@PathParam("id") final Long id, final Contact contact) {
    final Contact updated = contactCrudController.update(id, contact);
    return Response.ok(updated).build();
  }

  @DELETE
  @Path("{id}")
  public Response delete(@PathParam("id") final Long id) {
    contactCrudController.delete(id);
    return Response.ok().build();
  }
}
