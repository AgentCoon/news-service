package com.agentcoon.news.api;

public class TopHeadlinesSearchDto {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final Integer pageSize;
    private final Integer page;
    private final String query;
    private final String country;
    private final String category;

    private TopHeadlinesSearchDto(Integer pageSize, Integer page, String query, String country, String category) {
        this.pageSize = pageSize;
        this.page = page;
        this.query = query;
        this.country = country;
        this.category = category;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public String getQuery() {
        return query;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "TopHeadlinesSearch [query=" + query + ", country=" + country + ", category=" + category +
                ", page=" + page + ", page size=" + pageSize + "]";
    }

    public static class Builder {

        private Integer pageSize = DEFAULT_PAGE_SIZE;
        private Integer page = DEFAULT_PAGE;
        private String query;
        private String country;
        private String category;

        public static Builder aTopHeadlinesSearchDto() {
            return new Builder();
        }

        public Builder withPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public TopHeadlinesSearchDto build() {
            return new TopHeadlinesSearchDto(pageSize, page, query, country, category);
        }
    }
}
