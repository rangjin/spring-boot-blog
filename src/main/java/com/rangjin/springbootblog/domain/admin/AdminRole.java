package com.rangjin.springbootblog.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminRole {

    Admin("ROLE_ADMIN"),
    Waiting("ROLE_WAITING");

    private final String value;

}
