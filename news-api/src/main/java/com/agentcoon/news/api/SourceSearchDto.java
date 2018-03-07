package com.agentcoon.news.api;

public class SourceSearchDto {

    private final String category;
    private final String language;
    private final String country;

    private SourceSearchDto(String category, String language, String country) {
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

        public static Builder aSourceSearchDto() {
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

        public SourceSearchDto build() {
            return new SourceSearchDto(category, language, country);
        }
    }
}
