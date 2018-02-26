package com.agentcoon.news.app.dropwizard.configuration;

import com.agentcoon.news.news.client.NewsApiConfiguration;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class NewsConfiguration extends Configuration {

    @NotNull
    private Boolean allowCORS;

    private NewsApiConfiguration newsApiConfiguration;

    public Boolean getAllowCORS() {
        return allowCORS;
    }

    public NewsApiConfiguration getNewsApiConfiguration() {
        return newsApiConfiguration;
    }
}
