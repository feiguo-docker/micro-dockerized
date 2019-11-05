package com.nttdata.demo.boundary;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("status")
@ApplicationScoped
public class StatusResource {

  @GET
  @Path("ping")
  public Response ping() {
    return Response.ok().build();
  }

}
