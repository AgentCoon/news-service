package com.agentcoon.news.app.dropwizard;

import com.agentcoon.news.app.dropwizard.configuration.NewsConfiguration;
import com.agentcoon.news.news.client.NewsApiClient;
import com.agentcoon.news.news.client.NewsApiClientFactory;
import com.agentcoon.news.news.client.NewsApiConfiguration;
import com.agentcoon.news.news.client.NewsApiGateway;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class NewsApiAccessModule extends AbstractModule {

    @Provides
    @Singleton
    NewsApiGateway newsApiGateway(NewsConfiguration config) {

        NewsApiConfiguration newsApiConfiguration = config.getNewsApiConfiguration();

        NewsApiClient newsApiClient = new NewsApiClientFactory(buildObjectMapper())
                .create(newsApiConfiguration);

        return new NewsApiGateway(newsApiClient, newsApiConfiguration.getApiKey());
    }

    @Override
    protected void configure() {
    }

    private ObjectMapper buildObjectMapper() {
        return new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
