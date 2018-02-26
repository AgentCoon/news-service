package com.agentcoon.news.news.client;

import com.agentcoon.news.news.client.api.SourcesResponseDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiClient {

    @GET("top-headlines")
    Call<TopHeadlinesResponseDto> searchTopHeadlines(@Query("q") String query,
                                                     @Query("country") String country,
                                                     @Query("category") String category,
                                                     @Query("page") Integer page,
                                                     @Query("pageSize") Integer pageSize,
                                                     @Query("apiKey") String apiKey);

    @GET("sources")
    Call<SourcesResponseDto> searchSources(@Query("category") String category,
                                           @Query("country") String country,
                                           @Query("language") String language,
                                           @Query("apiKey") String apiKey);
}
