package com.agentcoon.news.acceptancetest

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Files
import java.nio.file.Paths

class TestUtil {

    private static final Logger logger = LoggerFactory.getLogger(TestUtil.class)

    static String dataFileToString(String name) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/" + name)))
        } catch (IOException e) {
            logger.error("IOException", e)
            throw new RuntimeException("IOException", e)
        }
    }
}
