package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.post.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private PostStatus status;
    private Long categoryId;

    @Builder
    public PostRequestDto(String title, String content, PostStatus status, Long categoryId) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.categoryId = categoryId;
    }

}
