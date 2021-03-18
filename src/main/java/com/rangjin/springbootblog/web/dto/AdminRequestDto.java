package com.rangjin.springbootblog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminRequestDto {

    private String username;
    private String password;
    private String rePassword;

    @Builder
    public AdminRequestDto(String username, String password, String rePassword) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
    }

}
