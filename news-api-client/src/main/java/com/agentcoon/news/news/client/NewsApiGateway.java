package com.agentcoon.news.news.client;

import com.agentcoon.news.news.client.api.SourcesResponseDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import com.agentcoon.news.news.client.exception.NewsApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;

public class NewsApiGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NewsApiClient newsApiClient;
    private final String apiKey;

    @Inject
    public NewsApiGateway(NewsApiClient newsApiClient, String apiKey) {
        this.newsApiClient = newsApiClient;
        this.apiKey = apiKey;
    }

    public TopHeadlinesResponseDto searchTopHeadlines(String query, String country, String category, Integer page, Integer pageSize) throws NewsApiClientException {
        return send(newsApiClient.searchTopHeadlines(query, country, category, page, pageSize, apiKey), "failed to get top headlines from News API");
    }

    public SourcesResponseDto searchSources(String category, String country, String language) throws NewsApiClientException {
        return send(newsApiClient.searchSources(category, country, language, apiKey), "failed to get article sources from News API");
    }

    private <T> T send(Call<T> call, String errorMsg) throws NewsApiClientException {

        try {
            Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                logger.error("Error response {} from News API, {}", response, errorMsg);
                throw new NewsApiClientException(errorMsg);
            }

            return response.body();

        } catch (IOException e) {
            logger.error("IOException, {}. {}", errorMsg, e);
            throw new NewsApiClientException(errorMsg);
        }
    }
}
