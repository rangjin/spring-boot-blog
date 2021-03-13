package com.rangjin.springbootblog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDto {

    private String name;

    @Builder
    public CategoryRequestDto(String name) {
        this.name = name;
    }

}
