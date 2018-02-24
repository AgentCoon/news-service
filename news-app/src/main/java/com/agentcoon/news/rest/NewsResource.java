package com.agentcoon.news.rest;

import com.agentcoon.news.api.ErrorDto;
import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.search.NewsGatewayException;
import com.agentcoon.news.domain.news.search.TopHeadlinesSearchService;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;
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

@Path("/v1/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TopHeadlinesSearchService topHeadlinesSearchService;
    private final SourceDtoMapper sourceDtoMapper;
    private final ArticleDtoMapper articleMapper;

    @Inject
    public NewsResource(TopHeadlinesSearchService topHeadlinesSearchService, SourceDtoMapper sourceDtoMapper, ArticleDtoMapper articleMapper) {
        this.topHeadlinesSearchService = topHeadlinesSearchService;
        this.sourceDtoMapper = sourceDtoMapper;
        this.articleMapper = articleMapper;
    }

    @GET
    public Response searchTopHeadlines(@QueryParam("query") String query,
                                       @QueryParam("country") String country,
                                       @QueryParam("category") String category,
                                       @QueryParam("page") Integer page,
                                       @QueryParam("pageSize") Integer pageSize) {

        TopHeadlinesSearch searchQuery = aTopHeadlinesSearch()
                .withQuery(query)
                .withCountry(country)
                .withCategory(category)
                .withPage(page)
                .withPageSize(pageSize)
                .build();

        logger.debug("Top headlines search {}", searchQuery);

        try {
            return searchTopHeadlines(searchQuery);
        } catch (NewsGatewayException a) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                    .entity(new ErrorDto("An error occurred while processing your request")).build();
        }
    }

    @GET
    @Path("/{country}/{category}")
    public Response getTopHeadlines(@PathParam("country") String country, @PathParam("category") String category) throws NewsGatewayException {

        TopHeadlinesSearch searchQuery = aTopHeadlinesSearch()
                .withCountry(country)
                .withCategory(category)
                .build();

        return getTopHeadlines(searchQuery);
    }

    @GET
    @Path("/sources")
    public Response searchSources(@QueryParam("category") String category,
                                  @QueryParam("country") String country,
                                  @QueryParam("language") String language) throws NewsGatewayException {

        SourceSearch sourceSearch = aSourceSearch()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language)
                .build();

        logger.debug("Sources search {}", sourceSearch);

        return searchSources(sourceSearch);
    }

    Response searchTopHeadlines(TopHeadlinesSearch searchQuery) throws NewsGatewayException {
        List<Article> topHeadlines = topHeadlinesSearchService.searchTopHeadlineNews(searchQuery);

        return Response.ok(topHeadlines.stream()
                .map(articleMapper::from)
                .collect(toList())).build();
    }

    Response getTopHeadlines(TopHeadlinesSearch searchQuery) throws NewsGatewayException {
        List<Article> top = topHeadlinesSearchService.searchTopHeadlineNews(searchQuery);

        return Response.ok(articleMapper.from(top, searchQuery.getCountry(), searchQuery.getCategory())).build();
    }

    Response searchSources(SourceSearch sourceSearch) throws NewsGatewayException {
        List<Source> sources = topHeadlinesSearchService.searchSources(sourceSearch);

        return Response.ok(sources.stream()
                .map(sourceDtoMapper::from)
                .collect(toList())).build();
    }
}
