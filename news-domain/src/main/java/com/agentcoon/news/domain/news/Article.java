package com.agentcoon.news.domain.news;

import java.time.LocalDate;

public class Article {

    private final String author;
    private final String title;
    private final String description;
    private final LocalDate date;
    private final String source;
    private final String articleUrl;
    private final String imageUrl;

    private Article(String author, String title, String description, LocalDate date, String source, String articleUrl, String imageUrl) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.date = date;
        this.source = source;
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

    public String getSource() {
        return source;
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

        public static Builder anArticle() {
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

       public Article build() {
            return new Article(author, title, description, date, source, articleUrl, imageUrl);
       }
    }
}
