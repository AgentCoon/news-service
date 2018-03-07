package com.agentcoon.news.rest;

import com.agentcoon.news.api.ArticleDto;
import com.agentcoon.news.api.TopHeadlinesDto;
import com.agentcoon.news.news.client.api.NewsApiArticleDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import org.junit.Test;

import java.time.*;
import java.util.Collections;

import static com.agentcoon.news.news.client.api.NewsApiArticleDto.Builder.aNewsApiArticleDto;
import static com.agentcoon.news.news.client.api.NewsApiSourceDto.Builder.aNewsApiSourceDto;
import static org.junit.Assert.assertEquals;

public class ArticleDtoMapperTest {

    private ArticleDtoMapper mapper = new ArticleDtoMapper();

    @Test
    public void from() {
        String author = "Author";
        String country = "gb";
        String category = "sport";
        String source = "Source name";
        String title = "Title";
        String description = "Description";
        String articleUrl = "http://www.url.com";
        String imageUrl = "http://www.image.com";
        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.of(2016, Month.AUGUST, 8, 12, 45, 00), ZoneOffset.UTC);

        LocalDate expectedPublishedDate = LocalDate.of(2016, Month.AUGUST, 8);

        NewsApiArticleDto article = aNewsApiArticleDto()
                .withAuthor(author)
                .withTitle(title)
                .withDescription(description)
                .withPublishedAt(offsetDateTime)
                .withSource(aNewsApiSourceDto().withName(source).build())
                .withUrl(articleUrl)
                .withUrlToImage(imageUrl).build();

        TopHeadlinesResponseDto topHeadlinesResponseDto = new TopHeadlinesResponseDto(Collections.singletonList(article));

        TopHeadlinesDto dto = mapper.from(topHeadlinesResponseDto, country, category);
        assertEquals(country, dto.getCountry());
        assertEquals(category, dto.getCategory());
        assertEquals(1, dto.getArticles().size());

        ArticleDto articleDto = dto.getArticles().get(0);
        assertEquals(author, articleDto.getAuthor());
        assertEquals(title, articleDto.getTitle());
        assertEquals(description, articleDto.getDescription());
        assertEquals(expectedPublishedDate, articleDto.getDate());
        assertEquals(source, articleDto.getSourceName());
        assertEquals(articleUrl, articleDto.getArticleUrl());
        assertEquals(imageUrl, articleDto.getImageUrl());
    }
}
