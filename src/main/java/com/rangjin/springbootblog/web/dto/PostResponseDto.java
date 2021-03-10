package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.post.Post;
import com.rangjin.springbootblog.domain.post.PostStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final PostStatus status;
    private final String categoryName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.status = post.getStatus();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.categoryName = post.getCategory().getName();
    }

}
