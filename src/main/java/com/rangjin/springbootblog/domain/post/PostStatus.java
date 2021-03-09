package com.rangjin.springbootblog.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {

    Activated("공개"),
    Deactivated("비공개");

    private final String value;

}
