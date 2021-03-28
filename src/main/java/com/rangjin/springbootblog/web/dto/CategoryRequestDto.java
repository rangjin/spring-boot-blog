package com.rangjin.springbootblog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "카테고리 이름을 입력해주세요")
    private String name;

    @Builder
    public CategoryRequestDto(String name) {
        this.name = name;
    }

}
