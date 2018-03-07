package com.agentcoon.news.acceptancetest

import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification

import static com.jayway.restassured.RestAssured.given

class NewsServiceDriver {

    RequestSpecification appSpec = new RequestSpecBuilder().setBaseUri(TestParams.APP_BASE_URL)
            .setPort(TestParams.APP_PORT).setBasePath(TestParams.APP_CONTEXT_PATH).build()

    String topHeadlinesAreFoundForCountryAndCategory(String country, String category) {

        return given().spec(appSpec)
                .expect()
                .statusCode(200)
                .when()
                .get("/v1/news/{country}/{category}", country, category).asString()
    }

    String topHeadlinesAreFoundForFilteredQuery(String country, String category, String query, String page, String pageSize) {

        return given().spec(appSpec)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .expect()
                .statusCode(200)
                .when()
                .get("/v1/news/{country}/{category}", country, category).asString()
    }

    void topHeadlinesSearchReturnsHTTPError(String country, String category, Integer pageSize, Integer status) {

        given().spec(appSpec)
                .queryParam("pageSize", pageSize)
                .expect()
                .statusCode(status)
                .when()
                .get("/v1/news/{country}/{category}", country, category).asString()
    }

    String sourcesAreFoundForCountry(String country) {

        return given().spec(appSpec)
                .queryParam("country", country)
                .expect()
                .statusCode(200)
                .when()
                .get("/v1/news/sources").asString()
    }
}
