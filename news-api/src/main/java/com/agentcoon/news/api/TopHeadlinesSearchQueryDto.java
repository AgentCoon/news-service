package com.agentcoon.news.api;

import javax.ws.rs.QueryParam;

public class TopHeadlinesSearchQueryDto {

    @QueryParam("query")
    private String query;

    @QueryParam("country")
    private String country;

    @QueryParam("category")
    private String category;

    @QueryParam("page")
    private Integer page;

    @QueryParam("pageSize")
    private Integer pageSize;

    public TopHeadlinesSearchQueryDto() {
    }

    private TopHeadlinesSearchQueryDto(String query, String country, String category, Integer page, Integer pageSize) {
        this.query = query;
        this.country = country;
        this.category = category;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "TopHeadlinesSearchQueryDto [query=" + query + ", country=" + country + ", category=" + category +
                ", page=" + page + ", page size=" + pageSize + "]";
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

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public static final class Builder {

        private String query;
        private String country;
        private String category;
        private Integer page;
        private Integer pageSize;

        public static Builder aTopHeadlinesSearchQueryDto() {
            return new Builder();
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

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public TopHeadlinesSearchQueryDto build() {
            return new TopHeadlinesSearchQueryDto(query, country, category, page, pageSize);
        }
    }
}
