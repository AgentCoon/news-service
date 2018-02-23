package com.agentcoon.news.app.dropwizard.configuration;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class NewsConfiguration extends Configuration {

    @NotNull
    private Boolean allowCORS;

    public Boolean getAllowCORS() {
        return allowCORS;
    }
}
