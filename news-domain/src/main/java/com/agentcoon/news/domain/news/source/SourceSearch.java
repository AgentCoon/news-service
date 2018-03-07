package com.agentcoon.news.domain.news.source;

public class SourceSearch {

    private final String category;
    private final String language;
    private final String country;

    private SourceSearch(String category, String language, String country) {
        this.category = category;
        this.language = language;
        this.country = country;
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

    @Override
    public String toString() {
        return "SourceSearch [category=" + category+ ", country=" + country + ", language=" + language + "]";
    }

    public static final class Builder {

        private String category;
        private String language;
        private String country;

        public static Builder aSourceSearch() {
            return new Builder();
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

        public SourceSearch build() {
            return new SourceSearch(category, language, country);
        }
    }
}
