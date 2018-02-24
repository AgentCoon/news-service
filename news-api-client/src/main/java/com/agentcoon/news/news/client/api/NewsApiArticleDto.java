package com.agentcoon.news.news.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiArticleDto {

    private String author;
    private String title;
    private String description;
    private OffsetDateTime publishedAt;
    private NewsApiSourceDto source;
    private String url;
    private String urlToImage;

    public NewsApiArticleDto() {
    }

    public NewsApiArticleDto(String author, String title, String description, OffsetDateTime publishedAt, NewsApiSourceDto source, String url, String urlToImage) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.source = source;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public NewsApiSourceDto getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getSourceName() {
        return source.getName();
    }
}
