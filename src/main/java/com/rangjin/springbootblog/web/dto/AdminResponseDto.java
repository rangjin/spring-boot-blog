package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.admin.Admin;
import com.rangjin.springbootblog.domain.admin.AdminRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponseDto {

    private Long id;
    private String username;
    private String password;
    private AdminRole role;

    public AdminResponseDto(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.role = admin.getRole();
    }

}
