package com.rangjin.springbootblog.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryRequestDto {

    private final String name;

    @Builder
    public CategoryRequestDto(String name) {
        this.name = name;
    }

}
