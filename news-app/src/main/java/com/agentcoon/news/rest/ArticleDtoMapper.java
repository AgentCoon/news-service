package com.agentcoon.news.rest;

import com.agentcoon.news.api.ArticleDto;
import com.agentcoon.news.api.TopHeadlinesDto;
import com.agentcoon.news.news.client.api.NewsApiArticleDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;

import java.util.stream.Collectors;

import static com.agentcoon.news.api.ArticleDto.Builder.anArticleDto;
import static com.agentcoon.news.api.TopHeadlinesDto.Builder.topHeadlinesDto;

public class ArticleDtoMapper {

    public TopHeadlinesDto from(TopHeadlinesResponseDto topHeadlines, String country, String category) {
        return topHeadlinesDto()
                .withCategory(category)
                .withCountry(country)
                .withArticles(topHeadlines.getArticles().stream()
                        .map(this::from)
                        .collect(Collectors.toList()))
                .build();
    }

    private ArticleDto from(NewsApiArticleDto article) {
        return anArticleDto()
                .withTitle(article.getTitle())
                .withAuthor(article.getAuthor())
                .withDescription(article.getDescription())
                .withSource(article.getSourceName())
                .withDate(article.getPublishedDate())
                .withArticleUrl(article.getUrl())
                .withImageUrl(article.getUrlToImage()).build();
    }
}
