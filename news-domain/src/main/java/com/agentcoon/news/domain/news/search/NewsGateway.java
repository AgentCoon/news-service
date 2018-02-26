package com.agentcoon.news.domain.news.search;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;

import java.util.List;

public interface NewsGateway {

    List<Article> searchTopHeadlines(TopHeadlinesSearch topHeadlinesSearch) throws NewsGatewayException;

    List<Source> searchSources(SourceSearch sourceSearch) throws NewsGatewayException;
}
