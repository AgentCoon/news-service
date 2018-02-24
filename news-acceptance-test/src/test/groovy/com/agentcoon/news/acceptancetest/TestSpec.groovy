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
        mockNewsApi.newsMockResponse(country, category)

        then:
        newsService.searchTopHeadlines(country, category)
    }
}
