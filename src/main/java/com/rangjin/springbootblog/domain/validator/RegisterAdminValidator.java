package com.rangjin.springbootblog.domain.validator;

import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterAdminValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AdminRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AdminRequestDto dto = (AdminRequestDto) target;

        System.out.println(dto.getUsername());
        System.out.println(dto.getPassword());
        System.out.println(dto.getRePassword());

        if (dto.getUsername().equals("")) {
            errors.rejectValue("username", "required", "아이디를 입력하세요");
        }

        if (dto.getPassword().equals("")) {
            errors.rejectValue("password", "required", "비밀번호를 입력하세요");
        }

        if (dto.getRePassword().equals("")) {
            errors.rejectValue("rePassword", "required", "비밀번호를 다시 입력하세요");
        }
    }

}
