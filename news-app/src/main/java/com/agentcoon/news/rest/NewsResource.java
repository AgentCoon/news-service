package com.agentcoon.news.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {

    @GET
    @Path("/{lang}/{category}")
    public Response getRepository(@PathParam("lang") String lang, @PathParam("category")
            String category) {

        return Response.ok().build();
    }
}
