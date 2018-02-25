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

    public static final class Builder {
        private String author;
        private String title;
        private String description;
        private OffsetDateTime publishedAt;
        private NewsApiSourceDto source;
        private String url;
        private String urlToImage;

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPublishedAt(OffsetDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public Builder withSource(NewsApiSourceDto source) {
            this.source = source;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
            return this;
        }

        public NewsApiArticleDto build() {
            return new NewsApiArticleDto(author, title, description, publishedAt, source, url, urlToImage);
        }
    }
}
