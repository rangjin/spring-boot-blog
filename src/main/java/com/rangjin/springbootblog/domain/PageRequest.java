package com.rangjin.springbootblog.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Accessors(chain = true)
public class PageRequest {

    private int page;
    private int size;
    private Sort.Direction direction;
    private String properties;

    public void set(int page, int size, Sort.Direction direction, String properties) {
        this.page = Math.max(page - 1, 0);
        this.size = size > 50 ? 10 : size;
        this.direction = direction;
        this.properties = properties;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(this.page, this.size, this.direction, this.properties);
    }

}
