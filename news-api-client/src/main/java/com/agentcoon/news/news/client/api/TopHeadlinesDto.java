package com.agentcoon.news.news.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopHeadlinesDto {

    private String status;

    public TopHeadlinesDto() {
    }

    public TopHeadlinesDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
