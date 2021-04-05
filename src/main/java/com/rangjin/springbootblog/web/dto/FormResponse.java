package com.rangjin.springbootblog.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormResponse<T> {

    private boolean validated;
    private T data;

    public FormResponse(boolean validated, T data) {
        this.validated = validated;
        this.data = data;
    }

}
