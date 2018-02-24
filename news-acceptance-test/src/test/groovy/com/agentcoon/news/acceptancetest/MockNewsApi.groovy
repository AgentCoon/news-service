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

    void newsMockResponse(String country, String category) {

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_NEWS_CONTEXT_PATH + "top-headlines"))
                .withQueryParam("apiKey", equalTo("apiKey"))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("category", equalTo(category))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString(String.format("%s_%s.txt", country, category)))))
    }

    void afterStories() {
        wireMockServer.stop()
    }
}
