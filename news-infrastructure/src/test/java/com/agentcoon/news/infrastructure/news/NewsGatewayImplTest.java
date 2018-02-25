package com.agentcoon.news.infrastructure.news;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.search.NewsGatewayException;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;
import com.agentcoon.news.news.client.NewsApiGateway;
import com.agentcoon.news.news.client.api.SourcesResponseDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import com.agentcoon.news.news.client.exception.NewsApiClientException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.news.domain.news.Article.Builder.anArticle;
import static com.agentcoon.news.domain.news.TopHeadlinesSearch.Builder.aTopHeadlinesSearch;
import static com.agentcoon.news.domain.news.source.Source.Builder.aSource;
import static com.agentcoon.news.domain.news.source.SourceSearch.Builder.aSourceSearch;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class NewsGatewayImplTest {

    private NewsApiGateway newsApiGateway;
    private ArticleMapper articleMapper;
    private SourceMapper sourceMapper;

    private NewsGatewayImpl newsGateway;

    @Before
    public void setUp() {
        newsApiGateway = mock(NewsApiGateway.class);
        articleMapper = mock(ArticleMapper.class);
        sourceMapper = mock(SourceMapper.class);

        newsGateway = new NewsGatewayImpl(newsApiGateway, articleMapper, sourceMapper);
    }

    @Test
    public void searchTopHeadlines() throws NewsGatewayException, NewsApiClientException {
        String title = "Article title";
        String query = "searchQuery";
        String country = "de";
        Integer page = 0;
        Integer pageSize = 10;

        TopHeadlinesSearch topHeadlinesSearch = aTopHeadlinesSearch()
                .withQuery(query)
                .withCountry(country)
                .withPage(page)
                .withPageSize(pageSize).build();

        TopHeadlinesResponseDto topHeadlinesResponseDto = new TopHeadlinesResponseDto();
        Article article = anArticle().withTitle(title).build();

        when(articleMapper.from(topHeadlinesResponseDto)).thenReturn(Collections.singletonList(article));
        when(newsApiGateway.searchTopHeadlines(query, country, null, page, pageSize)).thenReturn(topHeadlinesResponseDto);

        List<Article> news = newsGateway.searchTopHeadlines(topHeadlinesSearch);
        assertEquals(title, news.get(0).getTitle());
    }

    @Test
            (expected=NewsGatewayException.class)
    public void searchTopHeadlinesWhenException() throws NewsApiClientException, NewsGatewayException {
        String query = "searchQuery";
        String country = "de";
        Integer page = 0;
        Integer pageSize = 10;

        TopHeadlinesSearch topHeadlinesSearch = aTopHeadlinesSearch()
                .withQuery(query)
                .withCountry(country)
                .withPage(page)
                .withPageSize(pageSize).build();

        doThrow(NewsApiClientException.class).when(newsApiGateway).searchTopHeadlines(query, country, null, page, pageSize);

        newsGateway.searchTopHeadlines(topHeadlinesSearch);
    }

    @Test
    public void searchSources() throws NewsGatewayException, NewsApiClientException {
        String description = "Source description";
        String country = "de";

        SourceSearch sourceSearch = aSourceSearch()
                .withCountry(country).build();

        SourcesResponseDto sourcesResponseDto = new SourcesResponseDto();
        Source source = aSource().withDescription(description).build();

        when(sourceMapper.from(sourcesResponseDto)).thenReturn(Collections.singletonList(source));
        when(newsApiGateway.searchSources(null, country, null)).thenReturn(sourcesResponseDto);

        List<Source> sources = newsGateway.searchSources(sourceSearch);
        assertEquals(description, sources.get(0).getDescription());
    }

    @Test
    public void searchSourcesWhenException() throws NewsApiClientException {
        String country = "de";

        SourceSearch sourceSearch = aSourceSearch()
                .withCountry(country).build();

        doThrow(NewsApiClientException.class).when(newsApiGateway).searchSources(null, country, null);

        try{
            newsGateway.searchSources(sourceSearch);
            fail("Should throw a NewsGatewayException");
        } catch(NewsGatewayException e) {
            verify(sourceMapper, never()).from(any());
        }
    }
}
