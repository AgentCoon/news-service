package com.agentcoon.news.api;

import java.util.List;

public class TopHeadlinesDto {

    private final String country;
    private final String category;
    private final List<ArticleDto> articles;

    private TopHeadlinesDto(String country, String category, List<ArticleDto> articles) {
        this.country = country;
        this.category = category;
        this.articles = articles;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    public List<ArticleDto> getArticles() {
        return articles;
    }

    public static final class Builder {
        private String country;
        private String category;
        private List<ArticleDto> articles;

        public static Builder localTopHeadlinesDto() {
            return new Builder();
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder withArticles(List<ArticleDto> articles) {
            this.articles = articles;
            return this;
        }

        public TopHeadlinesDto build() {
            return new TopHeadlinesDto(country, category, articles);
        }
    }
}
