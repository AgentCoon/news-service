package com.agentcoon.news.rest;

import com.agentcoon.news.api.SourceSearchDto;
import com.agentcoon.news.api.TopHeadlinesSearchDto;
import com.agentcoon.news.news.client.NewsApiGateway;
import com.agentcoon.news.news.client.api.SourcesResponseDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import com.agentcoon.news.news.client.exception.NewsApiClientException;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.agentcoon.news.domain.news.TopHeadlinesSearch.Builder.aTopHeadlinesSearch;
import static com.agentcoon.news.domain.news.source.SourceSearch.Builder.aSourceSearch;
import static java.util.stream.Collectors.toList;

import static com.agentcoon.news.api.SourceSearchDto.Builder.aSourceSearchDto;
import static com.agentcoon.news.api.TopHeadlinesSearchDto.Builder.aTopHeadlinesSearchDto;

@Path("/v1/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SourceDtoMapper sourceDtoMapper;
    private final ArticleDtoMapper articleMapper;
    private final NewsApiGateway newsApiGateway;

    @Inject
    public NewsResource(SourceDtoMapper sourceDtoMapper, ArticleDtoMapper articleMapper, NewsApiGateway newsApiGateway) {
        this.sourceDtoMapper = sourceDtoMapper;
        this.articleMapper = articleMapper;
        this.newsApiGateway = newsApiGateway;
    }

    @GET
    @Path("/{country}/{category}")
    public Response searchTopHeadlines(@PathParam("country") String country,
                                       @PathParam("category") String category,
                                       @QueryParam("query") String query,
                                       @QueryParam("page") Integer page,
                                       @QueryParam("pageSize") Integer pageSize) {

        if (pageSize != null && pageSize > 100) {
            throw new NewsApiClientException("Page size must be less than or equal to 100", 400);
        }

        TopHeadlinesSearchDto searchQuery = aTopHeadlinesSearchDto()
                .withQuery(query)
                .withCountry(country)
                .withCategory(category)
                .withPage(page)
                .withPageSize(pageSize)
                .build();

        logger.debug("Top headlines search {}", searchQuery);

        return getTopHeadlines(searchQuery);
    }

    @GET
    @Path("/sources")
    public Response searchSources(@QueryParam("category") String category,
                                  @QueryParam("country") String country,
                                  @QueryParam("language") String language) {

        SourceSearchDto sourceSearch = aSourceSearchDto()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language)
                .build();

        logger.debug("Sources search {}", sourceSearch);

        return getSources(sourceSearch);
    }

    Response getTopHeadlines(TopHeadlinesSearchDto searchQuery) {
        TopHeadlinesResponseDto topHeadlines = newsApiGateway.searchTopHeadlines(searchQuery);

        return Response.ok(articleMapper.from(topHeadlines, searchQuery.getCountry(), searchQuery.getCategory())).build();
    }

    Response getSources(SourceSearchDto sourceSearch) {
        SourcesResponseDto sources = newsApiGateway.searchSources(sourceSearch);

        return Response.ok(sourceDtoMapper.from(sources)).build();
    }
}
