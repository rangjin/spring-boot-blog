package com.rangjin.springbootblog.advice;

import com.rangjin.springbootblog.advice.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exception() {
        return new ResponseEntity<>(new ErrorResponse(1000, "예기치 못한 오류가 발생했습니다"),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomPostNotFoundException.class)
    protected ResponseEntity<ErrorResponse> postNotFoundException() {
        return new ResponseEntity<>(new ErrorResponse(1001, "해당 포스트가 존재하지 않습니다"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomCategoryNotFoundException.class)
    protected ResponseEntity<ErrorResponse> categoryNotFoundException() {
        return new ResponseEntity<>(new ErrorResponse(1002, "해당 카테고리가 존재하지 않습니다"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomAdminNotFoundException.class)
    protected ResponseEntity<ErrorResponse> userNotFoundException() {
        return new ResponseEntity<>(new ErrorResponse(1003, "해당 관리자가 존재하지 않습니다"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomAuthenticationEntryPointException.class)
    protected ResponseEntity<ErrorResponse> authenticationEntryPointException() {
        return new ResponseEntity<>(new ErrorResponse(1004, "다시 로그인 한 후 이용해 주십시오"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> accessDeniedException() {
        return new ResponseEntity<>(new ErrorResponse(1005, "회원가입 승인 대기 상태입니다"),
                HttpStatus.FORBIDDEN);
    }

}
