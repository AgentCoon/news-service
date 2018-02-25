package com.agentcoon.news.rest;

import com.agentcoon.news.api.ArticleDto;
import com.agentcoon.news.api.TopHeadlinesDto;
import com.agentcoon.news.domain.news.Article;

import java.util.List;
import java.util.stream.Collectors;

import static com.agentcoon.news.api.ArticleDto.Builder.anArticleDto;
import static com.agentcoon.news.api.TopHeadlinesDto.Builder.topHeadlinesDto;

public class ArticleDtoMapper {

    public TopHeadlinesDto from(List<Article> topHeadlines, String country, String category) {
        return topHeadlinesDto()
                .withCategory(category)
                .withCountry(country)
                .withArticles(topHeadlines.stream()
                        .map(this::from)
                        .collect(Collectors.toList()))
                .build();
    }

    private ArticleDto from(Article article) {
        return anArticleDto()
                .withTitle(article.getTitle())
                .withAuthor(article.getAuthor())
                .withDescription(article.getDescription())
                .withSource(article.getSource())
                .withDate(article.getPublishedDate())
                .withArticleUrl(article.getArticleUrl())
                .withImageUrl(article.getImageUrl()).build();
    }
}
