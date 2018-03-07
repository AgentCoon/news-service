package com.agentcoon.news.acceptancetest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

class TestSpec extends Specification {

    Logger logger = LoggerFactory.getLogger(getClass())

    @Shared
    NewsServiceDriver newsService = new NewsServiceDriver()

    @Shared
    MockNewsApi mockNewsApi = new MockNewsApi()

    def setupSpec() {
        mockNewsApi.beforeStories()
    }

    def cleanupSpec() {
        mockNewsApi.afterStories()
    }

    def "Should get top headlines for country and category"() {
        logger.info("TestSpec: Should get top headlines for country and category")

        String country = "pl"
        String category = "health"

        when:
        mockNewsApi.mockNewsResponseForCountryAndCategory(country, category, TestUtil.dataFileToString("pl_health.txt"))

        then:
        String response = newsService.topHeadlinesAreFoundForCountryAndCategory(country, category)

        expect:
        response == TestUtil.dataFileToString("pl_health_expected.txt")
    }

    def "Should get top headlines for a filtered query"() {
        logger.info("TestSpec: Should get top headlines for a filtered query")

        String query = "flu"
        String country = "pl"
        String category = "health"
        String page = "1"
        String pageSize = "8"

        when:
        mockNewsApi.mockNewsResponseForFilteredQuery(query, country, category, page, pageSize, TestUtil.dataFileToString("pl_health.txt"))

        then:
        String response = newsService.topHeadlinesAreFoundForFilteredQuery(country, category, query, page, pageSize)

        expect:
        response == TestUtil.dataFileToString("pl_health_expected.txt")
    }

    def "Should return an error when page size greater than 100"() {
        logger.info("TestSpec: Should return an error when page size greater than 100")

        when:
        String country = "pl"
        String category = "health"
        Integer pageSize = 800

        then:
        newsService.topHeadlinesSearchReturnsHTTPError(country, category, pageSize, 400)
    }

    def "Should return an error when News client fails"() {
        logger.info("TestSpec: Should return an error when News client fails")

        String country = "pl"
        String category = "health"
        Integer pageSize = 50

        when:
        mockNewsApi.mockNewsFailureResponse(country, category, 500)

        then:
        newsService.topHeadlinesSearchReturnsHTTPError(country, category, pageSize, 502)
    }

    def "Should get sources for a country"() {
        logger.info("TestSpec: Should get sources for a country")

        String country = "es"

        when:
        mockNewsApi.mockSourcesResponseForCountry(country, TestUtil.dataFileToString("source_es.txt"))

        then:
        String response = newsService.sourcesAreFoundForCountry(country)

        expect:
        response == TestUtil.dataFileToString("source_es_expected.txt")
    }
}
