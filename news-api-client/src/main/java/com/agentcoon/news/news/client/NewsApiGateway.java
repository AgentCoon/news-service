package com.agentcoon.news.news.client;

import com.agentcoon.news.news.client.api.TopHeadlinesDto;
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

    public TopHeadlinesDto getTopHeadlines(String country, String category) throws NewsApiClientException {
        return send(newsApiClient.getTopHeadlines(country, category, apiKey),
                String.format("failed to get top headlines for country: %s and category: %s", country, category));
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
