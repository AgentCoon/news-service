package com.agentcoon.news.rest;

import com.agentcoon.news.news.client.NewsApiGateway;
import com.agentcoon.news.news.client.exception.NewsApiClientException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {

    private final NewsApiGateway newsApiGateway;

    @Inject
    public NewsResource(NewsApiGateway newsApiGateway) {
        this.newsApiGateway = newsApiGateway;
    }

    @GET
    @Path("/{lang}/{category}")
    public Response getRepository(@PathParam("lang") String lang, @PathParam("category")
            String category) throws NewsApiClientException {

        return Response.ok(newsApiGateway.getTopHeadlines(lang, category)).build();
    }
}
