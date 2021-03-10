package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.category.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryResponseDto {

    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }

}
