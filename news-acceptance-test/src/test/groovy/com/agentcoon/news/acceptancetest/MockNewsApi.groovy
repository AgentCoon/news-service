package com.agentcoon.news.acceptancetest

import com.github.tomakehurst.wiremock.WireMockServer

import javax.ws.rs.core.MediaType

import static com.github.tomakehurst.wiremock.client.WireMock.*

class MockNewsApi {

    private WireMockServer wireMockServer

    void beforeStories() {
        wireMockServer = new WireMockServer(TestParams.MOCK_NEWS_PORT)
        wireMockServer.start()
    }

    void mockNewsResponseForCountryAndCategory(String country, String category) {

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_NEWS_CONTEXT_PATH + "top-headlines"))
                .withQueryParam("apiKey", equalTo("apiKey"))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("category", equalTo(category))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString(String.format("%s_%s.txt", country, category)))))
    }

    void mockNewsResponseForFilteredQuery(String query, String country, String category, String page, String pageSize) {

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_NEWS_CONTEXT_PATH + "top-headlines"))
                .withQueryParam("apiKey", equalTo("apiKey"))
                .withQueryParam("q", equalTo(query))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("category", equalTo(category))
                .withQueryParam("page", equalTo(page))
                .withQueryParam("pageSize", equalTo(pageSize))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString(String.format("%s_%s.txt", country, category)))))
    }

    void mockNewsFailureResponse(String country, String category) {

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_NEWS_CONTEXT_PATH + "top-headlines"))
                .withQueryParam("apiKey", equalTo("apiKey"))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("category", equalTo(category))
                .willReturn(aResponse()
                .withStatus(500)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString("newsErrorResponse.txt"))))
    }

    void mockSourcesResponseForCountry(String country) {

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_NEWS_CONTEXT_PATH + "sources"))
                .withQueryParam("apiKey", equalTo("apiKey"))
                .withQueryParam("country", equalTo(country))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString(String.format("source_%s.txt", country)))))
    }

    void afterStories() {
        wireMockServer.stop()
    }
}
