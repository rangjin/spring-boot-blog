package com.rangjin.springbootblog.domain.validator;

import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryRequestDto dto = (CategoryRequestDto) target;

        if (dto.getName().equals("")) {
            errors.rejectValue("name", "required", "카테고리 이름을 입력해주세요");
        }
    }

}
