package com.agentcoon.news.infrastructure.news;

import com.agentcoon.news.domain.news.Article;
import com.agentcoon.news.news.client.api.NewsApiArticleDto;
import com.agentcoon.news.news.client.api.NewsApiSourceDto;
import com.agentcoon.news.news.client.api.TopHeadlinesResponseDto;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArticleMapperTest {

    private ArticleMapper mapper = new ArticleMapper();

    @Test
    public void from() {
        String sourceName = "Source name";
        String author = "Author";
        String title = "Title";
        String description = "Description";
        OffsetDateTime publishedAt = OffsetDateTime.parse("2017-12-03T10:15:30+01:00");
        NewsApiSourceDto source = new NewsApiSourceDto.Builder().withName(sourceName).build();
        String url = "http://www.url.com";
        String urlToImage = "http://www.image.com";

        LocalDate expectedDate = LocalDate.of(2017, Month.DECEMBER, 3);

        NewsApiArticleDto newsApiArticleDto = new NewsApiArticleDto.Builder()
                .withAuthor(author)
                .withTitle(title)
                .withDescription(description)
                .withPublishedAt(publishedAt)
                .withSource(source)
                .withUrl(url)
                .withUrlToImage(urlToImage).build();

        TopHeadlinesResponseDto topHeadlinesResponseDto = new TopHeadlinesResponseDto(Collections.singletonList(newsApiArticleDto));

        List<Article> articles = mapper.from(topHeadlinesResponseDto);
        assertEquals(1, articles.size());

        Article article = articles.get(0);
        assertEquals(author, article.getAuthor());
        assertEquals(title, article.getTitle());
        assertEquals(description, article.getDescription());
        assertEquals(expectedDate, article.getPublishedDate());
        assertEquals(sourceName, article.getSource());
        assertEquals(url, article.getArticleUrl());
        assertEquals(urlToImage, article.getImageUrl());
    }
}
