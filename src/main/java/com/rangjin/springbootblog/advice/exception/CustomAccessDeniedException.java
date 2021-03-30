package com.rangjin.springbootblog.advice.exception;

public class CustomAccessDeniedException extends RuntimeException {

    public CustomAccessDeniedException() {
        super();
    }

    public CustomAccessDeniedException(String message) {
        super(message);
    }

    public CustomAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
