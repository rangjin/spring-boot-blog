package com.rangjin.springbootblog.service;

import com.rangjin.springbootblog.advice.exception.CustomCategoryNotFoundException;
import com.rangjin.springbootblog.domain.category.Category;
import com.rangjin.springbootblog.domain.category.CategoryRepository;
import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import com.rangjin.springbootblog.web.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .build();

        return new CategoryResponseDto(categoryRepository.save(category));
    }

    public CategoryResponseDto findById(Long id) {
        return new CategoryResponseDto(categoryRepository.findById(id)
                .orElseThrow(CustomCategoryNotFoundException::new));
    }

    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
            .map(CategoryResponseDto::new)
            .collect(Collectors.toList());
    }

    public CategoryResponseDto modify(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CustomCategoryNotFoundException::new);

        category.update(dto);

        return new CategoryResponseDto(categoryRepository.save(category));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
