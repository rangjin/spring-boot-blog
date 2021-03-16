package com.rangjin.springbootblog.domain.validator;

import com.rangjin.springbootblog.web.dto.PostRequestDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostRequestDto dto = (PostRequestDto) target;

        if (dto.getTitle().equals("")) {
            errors.rejectValue("title", "required", "제목을 입력해주세요");
        }

        if (dto.getCategoryId() == null) {
            errors.rejectValue("categoryId", "required", "카테고리를 선택해주세요");
        }

        if (dto.getStatus() == null) {
            errors.rejectValue("status", "required", "공개 상태를 선택해주세요");
        }
    }

}
