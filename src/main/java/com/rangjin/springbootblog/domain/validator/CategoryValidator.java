package com.rangjin.springbootblog.domain.validator;

import com.rangjin.springbootblog.service.CategoryService;
import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class CategoryValidator implements Validator {

    private final CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryRequestDto dto = (CategoryRequestDto) target;

        if (categoryService.existsByName(dto.getName())) {
            errors.rejectValue("name", "overlap", "이미 존재하는 카테고리 이름입니다");
        }

    }

}
