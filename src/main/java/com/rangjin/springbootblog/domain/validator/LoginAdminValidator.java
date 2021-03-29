package com.rangjin.springbootblog.domain.validator;

import com.rangjin.springbootblog.service.AdminService;
import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class LoginAdminValidator implements Validator {

    private final AdminService adminService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AdminRequestDto.LoginDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AdminRequestDto.LoginDto dto = (AdminRequestDto.LoginDto) target;

        if (!adminService.existsByUserName(dto.getUsername())) {
            errors.rejectValue("username", "noExists", "해당 아이디가 존재하지 않습니다.");
        }
    }

}
