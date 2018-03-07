package com.agentcoon.news.news.client.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiSourceDto {

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

    public NewsApiSourceDto() {
    }

    private NewsApiSourceDto(String id, String name, String description, String url, String category, String language, String country) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private String url;
        private String category;
        private String language;
        private String country;

        public static Builder aNewsApiSourceDto() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public NewsApiSourceDto build() {
            return new NewsApiSourceDto(id, name, description, url, category, language, country);
        }
    }
}
