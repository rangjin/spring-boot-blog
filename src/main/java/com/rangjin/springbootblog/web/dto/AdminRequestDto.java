package com.rangjin.springbootblog.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

public class AdminRequestDto {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class RegisterDto {
        @NotBlank(message = "아이디를 입력해주세요")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        @NotBlank(message = "비밀번호를 다시 입력해주세요")
        private String rePassword;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LoginDto {
        @NotBlank(message = "아이디를 입력해주세요")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

}
