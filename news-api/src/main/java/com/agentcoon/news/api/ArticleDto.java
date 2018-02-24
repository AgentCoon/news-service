package com.agentcoon.news.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ArticleDto {

    private String author;
    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String sourceName;
    private String articleUrl;
    private String imageUrl;

    public ArticleDto(String author, String title, String description, LocalDate date, String sourceName, String articleUrl, String imageUrl) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.date = date;
        this.sourceName = sourceName;
        this.articleUrl = articleUrl;
        this.imageUrl = imageUrl;
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

    public LocalDate getDate() {
        return date;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static final class Builder {
        private String author;
        private String title;
        private String description;
        private LocalDate date;
        private String source;
        private String articleUrl;
        private String imageUrl;

        public static Builder anArticleDto() {
            return new Builder();
        }

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

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ArticleDto build() {
            return new ArticleDto(author, title, description, date, source, articleUrl, imageUrl);
        }
    }
}
