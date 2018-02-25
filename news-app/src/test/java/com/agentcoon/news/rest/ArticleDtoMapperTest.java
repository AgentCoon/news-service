package com.agentcoon.news.rest;

import com.agentcoon.news.api.ArticleDto;
import com.agentcoon.news.api.TopHeadlinesDto;
import com.agentcoon.news.domain.news.Article;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static com.agentcoon.news.domain.news.Article.Builder.anArticle;
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
        LocalDate publishedDate = LocalDate.of(2016, Month.AUGUST, 8);

        Article article = anArticle()
                .withAuthor(author)
                .withTitle(title)
                .withDescription(description)
                .withPublishedDate(publishedDate)
                .withSource(source)
                .withArticleUrl(articleUrl)
                .withImageUrl(imageUrl).build();

        TopHeadlinesDto dto = mapper.from(Collections.singletonList(article), country, category);
        assertEquals(country, dto.getCountry());
        assertEquals(category, dto.getCategory());
        assertEquals(1, dto.getArticles().size());

        ArticleDto articleDto = dto.getArticles().get(0);
        assertEquals(author, articleDto.getAuthor());
        assertEquals(title, articleDto.getTitle());
        assertEquals(description, articleDto.getDescription());
        assertEquals(publishedDate, articleDto.getDate());
        assertEquals(source, articleDto.getSourceName());
        assertEquals(articleUrl, articleDto.getArticleUrl());
        assertEquals(imageUrl, articleDto.getImageUrl());
    }
}
