package com.rangjin.springbootblog.web.dto;

import com.rangjin.springbootblog.domain.admin.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminResponseDto {

    private Long id;
    private String username;
    private String password;
    private List<String> roles;

    public AdminResponseDto(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.roles = admin.getRoles();
    }

}
