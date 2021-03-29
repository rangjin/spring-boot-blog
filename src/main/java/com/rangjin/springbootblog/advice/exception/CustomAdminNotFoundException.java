package com.rangjin.springbootblog.advice.exception;

public class CustomAdminNotFoundException extends RuntimeException {

    public CustomAdminNotFoundException() {
        super();
    }

    public CustomAdminNotFoundException(String message) {
        super(message);
    }

    public CustomAdminNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
