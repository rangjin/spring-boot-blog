package com.rangjin.springbootblog.advice.exception;

public class CustomPostNotFoundException extends RuntimeException {

    public CustomPostNotFoundException() {
        super();
    }

    public CustomPostNotFoundException(String message) {
        super(message);
    }

    public CustomPostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
