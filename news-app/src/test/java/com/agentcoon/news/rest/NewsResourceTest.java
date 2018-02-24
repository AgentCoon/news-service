package com.agentcoon.news.rest;

import com.agentcoon.news.api.ArticleDto;
import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.search.NewsGatewayException;
import com.agentcoon.news.domain.news.search.TopHeadlinesSearchService;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static com.agentcoon.news.api.ArticleDto.Builder.anArticleDto;
import static com.agentcoon.news.domain.news.Article.Builder.anArticle;
import static com.agentcoon.news.domain.news.TopHeadlinesSearch.Builder.aTopHeadlinesSearch;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        when(topHeadlinesSearchService.searchTopHeadlineNews(searchQuery)).thenReturn(Collections.singletonList(article));
        when(articleMapper.from(article)).thenReturn(articleDto);

        Response response = newsResource.search(searchQuery);

        assertEquals(200, response.getStatus());
        List<Article> result = (List<Article>) response.getEntity();
        assertEquals(1, result.size());
    }

}
