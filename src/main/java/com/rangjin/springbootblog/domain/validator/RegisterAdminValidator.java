package com.rangjin.springbootblog.domain.validator;

import com.rangjin.springbootblog.service.AdminService;
import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class RegisterAdminValidator implements Validator {

    private final AdminService adminService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AdminRequestDto.RegisterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AdminRequestDto.RegisterDto dto = (AdminRequestDto.RegisterDto) target;

        if (adminService.existsByUserName(dto.getUsername())) {
            errors.rejectValue("username", "overlap", "사용할 수 없는 아이디입니다");
        }

        if (!dto.getPassword().equals(dto.getRePassword())) {
            errors.rejectValue("rePassword", "unMatched", "비밀번호가 일치하지 않습니다");
        }
    }

}
