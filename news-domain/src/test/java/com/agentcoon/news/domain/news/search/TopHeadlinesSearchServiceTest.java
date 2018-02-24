package com.agentcoon.news.domain.news.search;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.news.domain.news.Article.Builder.anArticle;
import static com.agentcoon.news.domain.news.TopHeadlinesSearch.Builder.aTopHeadlinesSearch;
import static com.agentcoon.news.domain.news.source.Source.Builder.aSource;
import static com.agentcoon.news.domain.news.source.SourceSearch.Builder.aSourceSearch;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopHeadlinesSearchServiceTest {

    private NewsGateway newsGateway;

    private TopHeadlinesSearchService topHeadlinesSearch;

    @Before
    public void setUp() {
        newsGateway = mock(NewsGateway.class);

        topHeadlinesSearch = new TopHeadlinesSearchService(newsGateway);
    }

    @Test
    public void searchTopHeadlineNews() throws NewsGatewayException {
        String title = "Title";

        TopHeadlinesSearch searchQuery = aTopHeadlinesSearch().build();
        Article article = anArticle().withTitle(title).build();

        when(newsGateway.searchTopHeadlines(searchQuery)).thenReturn(Collections.singletonList(article));

        List<Article> articles = topHeadlinesSearch.searchTopHeadlineNews(searchQuery);
        assertEquals(1, articles.size());
        assertEquals(title, articles.get(0).getTitle());
    }

    @Test
    public void searchSources() throws NewsGatewayException {
        String category = "Health";

        SourceSearch searchQuery = aSourceSearch().build();
        Source source = aSource().withCategory(category).build();

        when(newsGateway.searchSources(searchQuery)).thenReturn(Collections.singletonList(source));

        List<Source> sources = topHeadlinesSearch.searchSources(searchQuery);
        assertEquals(1, sources.size());
        assertEquals(category, sources.get(0).getCategory());
    }
}
