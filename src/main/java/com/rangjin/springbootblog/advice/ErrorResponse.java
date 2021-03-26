package com.rangjin.springbootblog.advice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int code;

    private String msg;

    public ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
