package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.post.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String content;

    @NotNull(message = "공개 상태를 선택해주세요")
    private PostStatus status;

    @NotNull(message = "카테고리를 선택해주세요")
    private Long categoryId;

    @Builder
    public PostRequestDto(String title, String content, PostStatus status, Long categoryId) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.categoryId = categoryId;
    }

}
