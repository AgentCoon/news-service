package com.agentcoon.news.app.dropwizard;

import com.agentcoon.news.app.dropwizard.configuration.NewsConfiguration;
import com.agentcoon.news.domain.news.search.NewsGateway;
import com.agentcoon.news.infrastructure.news.NewsGatewayImpl;
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
import com.google.inject.Scopes;
import com.google.inject.Singleton;

public class NewsApiAccessModule extends AbstractModule {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Provides
    @Singleton
    NewsApiGateway newsApiGateway(NewsConfiguration config) {

        NewsApiConfiguration newsApiConfiguration = config.getNewsApiConfiguration();

        NewsApiClient newsApiClient = new NewsApiClientFactory(objectMapper)
                .create(newsApiConfiguration);

        return new NewsApiGateway(newsApiClient, newsApiConfiguration.getApiKey());
    }

    @Override
    protected void configure() {

        bind(NewsGateway.class).to(NewsGatewayImpl.class).in(Scopes.SINGLETON);
    }
}
