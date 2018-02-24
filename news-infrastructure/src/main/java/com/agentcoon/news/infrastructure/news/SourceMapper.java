package com.agentcoon.news.infrastructure.news;

import com.agentcoon.news.domain.news.source.Source;
import com.agentcoon.news.news.client.api.NewsApiSourceDto;
import com.agentcoon.news.news.client.api.SourcesResponseDto;

import java.util.List;

import static com.agentcoon.news.domain.news.source.Source.Builder.aSource;
import static java.util.stream.Collectors.toList;

public class SourceMapper {

    public List<Source> from(SourcesResponseDto dto) {
        return dto.getSources().stream().map(this::from).collect(toList());
    }

    private Source from(NewsApiSourceDto dto) {
        return aSource().withId(dto.getId())
                .withCategory(dto.getCategory())
                .withCountry(dto.getCountry())
                .withDescription(dto.getDescription())
                .withName(dto.getName())
                .withLanguage(dto.getLanguage())
                .withUrl(dto.getUrl()).build();
    }
}
