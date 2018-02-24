package com.agentcoon.news.news.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopHeadlinesResponseDto {

    private List<NewsApiArticleDto> articles;

    public TopHeadlinesResponseDto() {
    }

    public TopHeadlinesResponseDto(List<NewsApiArticleDto> articles) {
        this.articles = articles;
    }

    public List<NewsApiArticleDto> getArticles() {
        return articles;
    }
}
