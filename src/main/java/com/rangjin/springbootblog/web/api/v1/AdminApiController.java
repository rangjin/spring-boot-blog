package com.rangjin.springbootblog.web.api.v1;

import com.rangjin.springbootblog.config.security.JwtTokenProvider;
import com.rangjin.springbootblog.domain.validator.LoginAdminValidator;
import com.rangjin.springbootblog.domain.validator.RegisterAdminValidator;
import com.rangjin.springbootblog.service.AdminService;
import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import com.rangjin.springbootblog.web.dto.AdminResponseDto;
import com.rangjin.springbootblog.web.dto.FormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final JwtTokenProvider jwtTokenProvider;

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid AdminRequestDto.RegisterDto dto, Errors errors) {
        new RegisterAdminValidator(adminService).validate(dto, errors);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        return new ResponseEntity<>(new FormResponse<>(true, adminService.register(dto)), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid AdminRequestDto.LoginDto dto, Errors errors) {
        new LoginAdminValidator(adminService).validate(dto, errors);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        AdminResponseDto responseDto = adminService.findByUsername(dto.getUsername());

        return new ResponseEntity<>(new FormResponse<>(true,
                jwtTokenProvider.createToken(
                        String.valueOf(responseDto.getId()), responseDto.getRole().getValue())),
                HttpStatus.OK);
    }

}
