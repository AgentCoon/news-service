package com.agentcoon.news.rest;

import com.agentcoon.news.api.SourceDto;
import com.agentcoon.news.news.client.api.NewsApiSourceDto;
import com.agentcoon.news.news.client.api.SourcesResponseDto;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SourceDtoMapperTest {

    private SourceDtoMapper mapper = new SourceDtoMapper();

    @Test
    public void from() {
        String id = "123";
        String name = "Source name";
        String description = "Description";
        String url = "http://www.url.com";
        String category = "category";
        String language = "es";
        String country = "Spain";

        NewsApiSourceDto newsApiSourceDto = new NewsApiSourceDto.Builder()
                .withId(id)
                .withName(name)
                .withDescription(description)
                .withUrl(url)
                .withCategory(category)
                .withLanguage(language)
                .withCountry(country)
                .build();

        SourcesResponseDto source = new SourcesResponseDto(Collections.singletonList(newsApiSourceDto));

        List<SourceDto> dtos = mapper.from(source);
        assertEquals(1, dtos.size());

        SourceDto dto = dtos.get(0);
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
        assertEquals(url, dto.getUrl());
        assertEquals(category, dto.getCategory());
        assertEquals(language, dto.getLanguage());
        assertEquals(country, dto.getCountry());
    }
}
