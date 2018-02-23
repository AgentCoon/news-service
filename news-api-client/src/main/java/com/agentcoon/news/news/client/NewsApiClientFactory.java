package com.agentcoon.news.news.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class NewsApiClientFactory {

    private final ObjectMapper objectMapper;

    public NewsApiClientFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public NewsApiClient create(NewsApiConfiguration config) {
        return getRetrofitInstance(config).create(NewsApiClient.class);
    }

    private Retrofit getRetrofitInstance(NewsApiConfiguration config) {
        return new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(config.getUrl())
                .addConverterFactory(
                        JacksonConverterFactory.create(objectMapper))
                .build();
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
