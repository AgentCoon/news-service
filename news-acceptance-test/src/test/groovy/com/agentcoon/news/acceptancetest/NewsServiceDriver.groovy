package com.agentcoon.news.acceptancetest

import com.agentcoon.news.api.SourceDto
import com.agentcoon.news.api.TopHeadlinesDto
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification

import static com.jayway.restassured.RestAssured.given
import static org.junit.Assert.assertEquals

class NewsServiceDriver {

    RequestSpecification appSpec = new RequestSpecBuilder().setBaseUri(TestParams.APP_BASE_URL)
            .setPort(TestParams.APP_PORT).setBasePath(TestParams.APP_CONTEXT_PATH).build()

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)


    void topHeadlinesAreFoundForCountryAndCategory(String country, String category) {

        String json = given().spec(appSpec)
                .expect()
                .statusCode(200)
                .when()
                .get("/v1/news/{country}/{category}", country, category).asString()

        TopHeadlinesDto dto = mapper.readValue(json, TopHeadlinesDto.class)
        assertEquals(dto.getCategory(), category)
        assertEquals(dto.getCountry(), country)
        assertEquals(2, dto.getArticles().size())
    }

    void topHeadlinesAreFoundForFilteredQuery(String country, String category, String query, String page, String pageSize) {

        String json = given().spec(appSpec)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .expect()
                .statusCode(200)
                .when()
                .get("/v1/news/{country}/{category}", country, category).asString()

        TopHeadlinesDto dto = mapper.readValue(json, TopHeadlinesDto.class)
        assertEquals(dto.getCategory(), category)
        assertEquals(dto.getCountry(), country)
        assertEquals(2, dto.getArticles().size())
    }

    void topHeadlinesSearchReturnsHTTPError(String country, String category, Integer pageSize, Integer status) {

        given().spec(appSpec)
                .queryParam("pageSize", pageSize)
                .expect()
                .statusCode(status)
                .when()
                .get("/v1/news/{country}/{category}", country, category).asString()
    }

    void sourcesAreFoundForCountry(String country) {

        String json = given().spec(appSpec)
                .queryParam("country", country)
                .expect()
                .statusCode(200)
                .when()
                .get("/v1/news/sources").asString()

        List<SourceDto> dtos = mapper.readValue(json, new TypeReference<List<SourceDto>>() {})
        assertEquals(2, dtos.size())
        assertEquals(country, dtos.get(0).getCountry())
    }
}
