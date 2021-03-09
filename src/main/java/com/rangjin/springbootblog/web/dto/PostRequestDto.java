package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.post.PostStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private final String title;
    private final String content;
    private final PostStatus status;

    @Builder
    public PostRequestDto(String title, String content, PostStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

}
