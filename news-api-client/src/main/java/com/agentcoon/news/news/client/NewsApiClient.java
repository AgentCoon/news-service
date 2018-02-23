package com.agentcoon.news.news.client;

import com.agentcoon.news.news.client.api.TopHeadlinesDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiClient {

    @GET("top-headlines")
    Call<TopHeadlinesDto> getTopHeadlines(@Query("country") String country, @Query("category")
            String category, @Query("apiKey") String apiKey);
}
