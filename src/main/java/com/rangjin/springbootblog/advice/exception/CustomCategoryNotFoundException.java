package com.rangjin.springbootblog.advice.exception;

public class CustomCategoryNotFoundException extends RuntimeException {

    public CustomCategoryNotFoundException() {
        super();
    }

    public CustomCategoryNotFoundException(String message) {
        super(message);
    }

    public CustomCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
