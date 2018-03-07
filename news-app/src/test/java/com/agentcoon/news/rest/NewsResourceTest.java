package com.agentcoon.news.rest;

import com.agentcoon.news.api.*;
import com.agentcoon.news.news.client.NewsApiGateway;
import com.agentcoon.news.news.client.api.NewsApiSourceDto;
import com.agentcoon.news.news.client.api.SourcesResponseDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import com.agentcoon.news.news.client.exception.NewsApiClientException;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static com.agentcoon.news.api.ArticleDto.Builder.anArticleDto;
import static com.agentcoon.news.api.SourceDto.Builder.aSourceDto;
import static com.agentcoon.news.api.SourceSearchDto.Builder.aSourceSearchDto;
import static com.agentcoon.news.api.TopHeadlinesDto.Builder.topHeadlinesDto;
import static com.agentcoon.news.api.TopHeadlinesSearchDto.Builder.aTopHeadlinesSearchDto;
import static com.agentcoon.news.news.client.api.NewsApiSourceDto.Builder.aNewsApiSourceDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class NewsResourceTest {

    private NewsApiGateway newsApiGateway;
    private ArticleDtoMapper articleMapper;
    private SourceDtoMapper sourceMapper;

    private NewsResource newsResource;

    @BeforeClass
    public static void ensureServiceLocatorPopulated() {
        // workaround for https://github.com/HubSpot/dropwizard-guice/issues/95
        JerseyGuiceUtils.reset();
    }

    @Before
    public void setUp() {
        newsApiGateway = mock(NewsApiGateway.class);
        articleMapper = mock(ArticleDtoMapper.class);
        sourceMapper = mock(SourceDtoMapper.class);

        newsResource = new NewsResource(sourceMapper, articleMapper, newsApiGateway);
    }

    @Test
    public void searchTopHeadlines() throws NewsApiClientException {
        String query = "searchString";
        String country = "pl";
        String category = "health";
        Integer page = 2;
        Integer pageSize = 24;

        TopHeadlinesSearchDto searchQuery = aTopHeadlinesSearchDto()
                .withQuery(query)
                .withCountry(country)
                .withCategory(category)
                .withPage(page)
                .withPageSize(pageSize)
                .build();

        ArticleDto articleDto = anArticleDto().build();
        TopHeadlinesDto topHeadlinesDto = topHeadlinesDto().withArticles(Collections.singletonList(articleDto)).build();

        TopHeadlinesResponseDto topHeadlinesResponseDto = new TopHeadlinesResponseDto();

        when(newsApiGateway.searchTopHeadlines(searchQuery)).thenReturn(topHeadlinesResponseDto);
        when(articleMapper.from(topHeadlinesResponseDto, country, category)).thenReturn(topHeadlinesDto);

        Response response = newsResource.getTopHeadlines(searchQuery);

        assertEquals(200, response.getStatus());
        TopHeadlinesDto result = (TopHeadlinesDto) response.getEntity();
        assertEquals(1, result.getArticles().size());
    }

    @Test
    public void searchTopHeadlinesWhenClientException() throws NewsApiClientException {
        String query = "searchString";
        String errorMessage = "An exception occurred";

        TopHeadlinesSearchDto searchQuery = aTopHeadlinesSearchDto()
                .withQuery(query)
                .build();

        doThrow(new NewsApiClientException(errorMessage)).when(newsApiGateway).searchTopHeadlines(searchQuery);

        try {
            newsResource.getTopHeadlines(searchQuery);
            fail("Expected NewsApiClientException");
        } catch (NewsApiClientException e) {
            Response response = e.getResponse();
            assertEquals(500, response.getStatus());

            ErrorDto errorDto = (ErrorDto) response.getEntity();
            assertEquals(errorMessage, errorDto.getMessage());
            verify(articleMapper, never()).from(any(), any(), any());
        }
    }

    @Test
    public void searchTopHeadlinesWhenInvalidPageSize() throws NewsApiClientException {
        String country = "pl";
        String category = "health";
        Integer pageSize = 568;

        try {
            newsResource.searchTopHeadlines(country, category, null, null, pageSize);
            fail("Expected NewsApiClientException");
        } catch (NewsApiClientException e) {
            Response response = e.getResponse();
            assertEquals(400, response.getStatus());
            verify(newsApiGateway, never()).searchTopHeadlines(any());
            verify(articleMapper, never()).from(any(), any(), any());
        }
    }

    @Test
    public void searchSources() throws NewsApiClientException {
        String language = "pl";
        String country = "pl";
        String category = "health";

        SourceSearchDto searchQuery = aSourceSearchDto()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language).build();

        NewsApiSourceDto sourceDto = aNewsApiSourceDto()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language).build();

        SourcesResponseDto source = new SourcesResponseDto(Collections.singletonList(sourceDto));

        SourceDto dto = aSourceDto()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language).build();

        when(newsApiGateway.searchSources(searchQuery)).thenReturn(source);
        when(sourceMapper.from(source)).thenReturn(Collections.singletonList(dto));

        Response response = newsResource.getSources(searchQuery);

        assertEquals(200, response.getStatus());
        List<SourceDto> result = (List<SourceDto>) response.getEntity();
        assertEquals(1, result.size());
    }
}
