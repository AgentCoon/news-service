package com.agentcoon.news.news.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class NewsApiConfiguration {

    @NotNull
    private final String url;

    @NotNull
    private final String apiKey;

    @JsonCreator
    public NewsApiConfiguration(@JsonProperty("url") String url, @JsonProperty("apiKey") String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }
}
