package com.agentcoon.news.domain.news.search;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;

import javax.inject.Inject;
import java.util.List;

public class TopHeadlinesSearchService {

    private final NewsGateway newsGateway;

    @Inject
    public TopHeadlinesSearchService(NewsGateway newsGateway) {
        this.newsGateway = newsGateway;
    }

    public List<Article> searchTopHeadlineNews(TopHeadlinesSearch search) throws NewsGatewayException {
        return newsGateway.searchTopHeadlines(search);
    }

    public List<Source> searchSources(SourceSearch search) throws NewsGatewayException {
        return newsGateway.searchSources(search);
    }
}
