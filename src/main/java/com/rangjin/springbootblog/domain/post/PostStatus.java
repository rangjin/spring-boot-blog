package com.rangjin.springbootblog.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {

    Public("공개"),
    Private("비공개"),
    Delete("삭제");

    private final String value;

}
