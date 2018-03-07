package com.agentcoon.news.rest;

import com.agentcoon.news.api.SourceDto;
import com.agentcoon.news.news.client.api.NewsApiSourceDto;
import com.agentcoon.news.news.client.api.SourcesResponseDto;

import java.util.List;

import static com.agentcoon.news.api.SourceDto.Builder.aSourceDto;
import static java.util.stream.Collectors.toList;

public class SourceDtoMapper {

    public List<SourceDto> from(SourcesResponseDto dto) {
        return dto.getSources().stream().map(this::from).collect(toList());
    }

    private SourceDto from(NewsApiSourceDto dto) {
        return aSourceDto().withId(dto.getId())
                .withCategory(dto.getCategory())
                .withCountry(dto.getCountry())
                .withDescription(dto.getDescription())
                .withName(dto.getName())
                .withLanguage(dto.getLanguage())
                .withUrl(dto.getUrl()).build();
    }
}
