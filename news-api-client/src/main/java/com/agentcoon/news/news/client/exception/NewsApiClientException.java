package com.agentcoon.news.news.client.exception;

import com.agentcoon.news.api.ErrorDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NewsApiClientException extends WebApplicationException {

    public NewsApiClientException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorDto(message))
                .build());
    }

    public NewsApiClientException(String message, int status) {
        super(Response.status(status)
                .entity(new ErrorDto(message))
                .build());
    }
}
