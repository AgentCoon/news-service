package com.agentcoon.news.news.client;

import com.agentcoon.news.api.SourceSearchDto;
import com.agentcoon.news.api.TopHeadlinesSearchDto;
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

    public TopHeadlinesResponseDto searchTopHeadlines(TopHeadlinesSearchDto topHeadlinesSearch) {
        return send(newsApiClient.searchTopHeadlines(topHeadlinesSearch.getQuery(), topHeadlinesSearch.getCountry(), topHeadlinesSearch.getCategory(),
                topHeadlinesSearch.getPage(), topHeadlinesSearch.getPageSize(), apiKey), "failed to get top headlines from News API");
    }

    public SourcesResponseDto searchSources(SourceSearchDto sourceSearch) {
        return send(newsApiClient.searchSources(sourceSearch.getCategory(), sourceSearch.getCountry(), sourceSearch.getLanguage(), apiKey),
                "failed to get article sources from News API");
    }

    private <T> T send(Call<T> call, String errorMsg) {

        try {
            Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                logger.error("Error response {} from News API, {}", response, errorMsg);

                switch(response.code()) {
                    case 400:
                    case 429:
                        throw new NewsApiClientException(String.format("%s. Reason: %s", errorMsg, response.message()), response.code());
                    case 500:
                        throw new NewsApiClientException(errorMsg, 502);
                    default:
                        throw new NewsApiClientException(errorMsg);
                }
            }

            return response.body();

        } catch (IOException e) {
            logger.error("IOException, {}. {}", errorMsg, e);
            throw new NewsApiClientException(errorMsg);
        }
    }
}
