package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.post.Post;
import com.rangjin.springbootblog.domain.post.PostStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private PostStatus status;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.status = post.getStatus();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.categoryId = post.getCategory().getId();
        this.categoryName = post.getCategory().getName();
    }

}
