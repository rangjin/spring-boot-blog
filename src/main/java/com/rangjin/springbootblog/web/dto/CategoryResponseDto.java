package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private String name;
    private int postCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.postCnt = category.getPostList() != null ? category.getPostList().size() : 0;
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }

}
