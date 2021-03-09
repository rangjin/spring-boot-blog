package com.rangjin.springbootblog.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private String content;

    @Builder
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
