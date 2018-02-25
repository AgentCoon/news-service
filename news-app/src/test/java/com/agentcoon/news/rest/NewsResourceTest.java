package com.agentcoon.news.rest;

import com.agentcoon.news.api.ArticleDto;
import com.agentcoon.news.api.SourceDto;
import com.agentcoon.news.api.TopHeadlinesDto;
import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.search.NewsGatewayException;
import com.agentcoon.news.domain.news.search.TopHeadlinesSearchService;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static com.agentcoon.news.api.ArticleDto.Builder.anArticleDto;
import static com.agentcoon.news.api.SourceDto.Builder.aSourceDto;
import static com.agentcoon.news.api.TopHeadlinesDto.Builder.topHeadlinesDto;
import static com.agentcoon.news.domain.news.Article.Builder.anArticle;
import static com.agentcoon.news.domain.news.TopHeadlinesSearch.Builder.aTopHeadlinesSearch;
import static com.agentcoon.news.domain.news.source.Source.Builder.aSource;
import static com.agentcoon.news.domain.news.source.SourceSearch.Builder.aSourceSearch;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NewsResourceTest {

    private TopHeadlinesSearchService topHeadlinesSearchService;
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
        topHeadlinesSearchService = mock(TopHeadlinesSearchService.class);
        articleMapper = mock(ArticleDtoMapper.class);
        sourceMapper = mock(SourceDtoMapper.class);

        newsResource = new NewsResource(topHeadlinesSearchService, sourceMapper, articleMapper);
    }

    @Test
    public void searchTopHeadlines() throws NewsGatewayException {
        String query = "searchString";
        String country = "pl";
        String category = "health";
        Integer page = 2;
        Integer pageSize = 24;

        TopHeadlinesSearch searchQuery = aTopHeadlinesSearch()
                .withQuery(query)
                .withCountry(country)
                .withCategory(category)
                .withPage(page)
                .withPageSize(pageSize)
                .build();

        Article article = anArticle().build();
        ArticleDto articleDto = anArticleDto().build();
        TopHeadlinesDto topHeadlinesDto = topHeadlinesDto().withArticles(Collections.singletonList(articleDto)).build();

        when(topHeadlinesSearchService.searchTopHeadlineNews(searchQuery)).thenReturn(Collections.singletonList(article));
        when(articleMapper.from(Collections.singletonList(article), country, category)).thenReturn(topHeadlinesDto);

        Response response = newsResource.getTopHeadlines(searchQuery);

        assertEquals(200, response.getStatus());
        TopHeadlinesDto result = (TopHeadlinesDto) response.getEntity();
        assertEquals(1, result.getArticles().size());
    }

    @Test
    public void searchTopHeadlinesWhenClientException() throws NewsGatewayException {
        String query = "searchString";

        TopHeadlinesSearch searchQuery = aTopHeadlinesSearch()
                .withQuery(query)
                .build();

        doThrow(NewsGatewayException.class).when(topHeadlinesSearchService).searchTopHeadlineNews(searchQuery);

        Response response = newsResource.getTopHeadlines(searchQuery);
        assertEquals(500, response.getStatus());
        verify(articleMapper, never()).from(any(), any(), any());
    }

    @Test
    public void searchSources() throws NewsGatewayException {
        String language = "pl";
        String country = "pl";
        String category = "health";

        SourceSearch searchQuery = aSourceSearch()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language).build();

        Source source = aSource().build();
        SourceDto sourceDto = aSourceDto()
                .withCategory(category)
                .withCountry(country)
                .withLanguage(language).build();

        when(topHeadlinesSearchService.searchSources(searchQuery)).thenReturn(Collections.singletonList(source));
        when(sourceMapper.from(source)).thenReturn(sourceDto);

        Response response = newsResource.getSources(searchQuery);

        assertEquals(200, response.getStatus());
        List<SourceDto> result = (List<SourceDto>) response.getEntity();
        assertEquals(1, result.size());
    }
}
