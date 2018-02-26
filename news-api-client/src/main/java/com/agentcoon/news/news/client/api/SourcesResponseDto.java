package com.agentcoon.news.news.client.api;

import java.util.List;

public class SourcesResponseDto {

    private List<NewsApiSourceDto> sources;

    public SourcesResponseDto() {
    }

    public SourcesResponseDto(List<NewsApiSourceDto> sources) {
        this.sources = sources;
    }

    public List<NewsApiSourceDto> getSources() {
        return sources;
    }
}
