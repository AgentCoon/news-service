package com.agentcoon.news.infrastructure.news;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.news.client.api.NewsApiArticleDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;

import java.util.List;

import static com.agentcoon.news.domain.news.Article.Builder.anArticle;
import static java.util.stream.Collectors.toList;

public class TopHeadlinesMapper {

    public List<Article> from(TopHeadlinesResponseDto dto) {
        return dto.getArticles().stream().map(this::from).collect(toList());
    }

    private Article from(NewsApiArticleDto dto) {
        return anArticle().withAuthor(dto.getAuthor())
                .withTitle(dto.getTitle())
                .withSource(dto.getSourceName())
                .withDescription(dto.getDescription())
                .withDate(dto.getPublishedAt().toLocalDate())
                .withArticleUrl(dto.getUrl())
                .withImageUrl(dto.getUrlToImage()).build();
    }
}
