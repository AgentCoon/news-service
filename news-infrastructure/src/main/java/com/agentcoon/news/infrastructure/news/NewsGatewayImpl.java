package com.agentcoon.news.infrastructure.news;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.domain.news.TopHeadlinesSearch;
import com.agentcoon.news.domain.news.search.NewsGateway;
import com.agentcoon.news.domain.news.search.NewsGatewayException;
import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.domain.news.source.SourceSearch;
import com.agentcoon.news.news.client.NewsApiGateway;
import com.agentcoon.news.news.client.api.SourcesResponseDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import com.agentcoon.news.news.client.exception.NewsApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class NewsGatewayImpl implements NewsGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NewsApiGateway newsApiGateway;
    private final TopHeadlinesMapper topHeadlinesMapper;
    private final SourceMapper sourceMapper;

    @Inject
    public NewsGatewayImpl(NewsApiGateway newsApiGateway, TopHeadlinesMapper topHeadlinesMapper, SourceMapper sourceMapper) {
        this.newsApiGateway = newsApiGateway;
        this.topHeadlinesMapper = topHeadlinesMapper;
        this.sourceMapper = sourceMapper;
    }

    @Override
    public List<Article> searchTopHeadlines(TopHeadlinesSearch topHeadlinesSearch) throws NewsGatewayException {

        try {
            TopHeadlinesResponseDto topHeadlines = newsApiGateway.searchTopHeadlines(topHeadlinesSearch.getQuery(),
                    topHeadlinesSearch.getCountry(), topHeadlinesSearch.getCategory(),
                    topHeadlinesSearch.getPage(), topHeadlinesSearch.getPageSize());

            return topHeadlinesMapper.from(topHeadlines);
        } catch (NewsApiClientException e) {
            logger.error("An error occurred when searching for top headlines. {}", topHeadlinesSearch, e);
            throw new NewsGatewayException("An error occurred while searching for top headlines.");
        }
    }

    @Override
    public List<Source> searchSources(SourceSearch sourceSearch) throws NewsGatewayException {

        try {
            SourcesResponseDto sources = newsApiGateway.searchSources(sourceSearch.getCategory(), sourceSearch.getCountry(), sourceSearch.getLanguage());

            return sourceMapper.from(sources);

        } catch (NewsApiClientException e) {
            logger.error("An error occurred when searching for sources. {}", sourceSearch, e);
            throw new NewsGatewayException("An error occurred while searching for sources.");
        }
    }
}
