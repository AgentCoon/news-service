package com.agentcoon.news.app.dropwizard;

import com.agentcoon.news.app.dropwizard.configuration.NewsConfiguration;
import com.agentcoon.news.news.client.NewsApiClient;
import com.agentcoon.news.news.client.NewsApiClientFactory;
import com.agentcoon.news.news.client.NewsApiConfiguration;
import com.agentcoon.news.news.client.NewsApiGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class NewsApiAccessModule extends AbstractModule {

    @Provides
    @Singleton
    NewsApiGateway newsApiGateway(NewsConfiguration config, ObjectMapper objectMapper) {

        NewsApiConfiguration newsApiConfiguration = config.getNewsApiConfiguration();

        NewsApiClient newsApiClient = new NewsApiClientFactory(objectMapper)
                .create(newsApiConfiguration);

        return new NewsApiGateway(newsApiClient, newsApiConfiguration.getApiKey());
    }

    @Override
    protected void configure() {
    }
}
