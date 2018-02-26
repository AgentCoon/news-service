package com.agentcoon.news.infrastructure.news;

import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.news.client.api.NewsApiSourceDto;
import com.agentcoon.news.news.client.api.SourcesResponseDto;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SourceMapperTest {

    private SourceMapper mapper = new SourceMapper();

    @Test
    public void from() {
        String id = "123";
        String name = "Source name";
        String description = "Description";
        String url = "http://www.url.com";
        String category = "category";
        String language = "es";
        String country = "Spain";

        NewsApiSourceDto sourceDto = new NewsApiSourceDto.Builder()
                .withId(id)
                .withName(name)
                .withDescription(description)
                .withUrl(url)
                .withCategory(category)
                .withLanguage(language)
                .withCountry(country).build();

        SourcesResponseDto dto = new SourcesResponseDto(Collections.singletonList(sourceDto));

        List<Source> sources = mapper.from(dto);
        assertEquals(1, sources.size());

        Source source = sources.get(0);

        assertEquals(id, source.getId());
        assertEquals(name, source.getName());
        assertEquals(description, source.getDescription());
        assertEquals(url, source.getUrl());
        assertEquals(category, source.getCategory());
        assertEquals(language, source.getLanguage());
        assertEquals(country, source.getCountry());
    }
}
