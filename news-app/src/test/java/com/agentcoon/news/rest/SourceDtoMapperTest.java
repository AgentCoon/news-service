package com.agentcoon.news.rest;

import com.agentcoon.news.api.SourceDto;
import com.agentcoon.news.domain.news.source.Source;
import org.junit.Test;

import static com.agentcoon.news.domain.news.source.Source.Builder.aSource;
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

        Source source = aSource().withId(id)
                .withName(name)
                .withDescription(description)
                .withUrl(url)
                .withCategory(category)
                .withLanguage(language)
                .withCountry(country).build();

        SourceDto dto = mapper.from(source);
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
        assertEquals(url, dto.getUrl());
        assertEquals(category, dto.getCategory());
        assertEquals(language, dto.getLanguage());
        assertEquals(country, dto.getCountry());
    }
}
