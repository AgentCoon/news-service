package com.agentcoon.news.rest;

import com.agentcoon.news.api.SourceDto;
import com.agentcoon.news.domain.news.source.Source;

import static com.agentcoon.news.api.SourceDto.Builder.aSourceDto;

public class SourceDtoMapper {

    public SourceDto from(Source source) {
        return aSourceDto()
                .withId(source.getId())
                .withName(source.getName())
                .withCategory(source.getCategory())
                .withCountry(source.getCountry())
                .withDescription(source.getDescription())
                .withLanguage(source.getLanguage())
                .withUrl(source.getUrl()).build();
    }
}
