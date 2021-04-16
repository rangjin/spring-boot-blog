package com.rangjin.springbootblog.web.api.v1;

import com.rangjin.springbootblog.advice.exception.CustomAccessDeniedException;
import com.rangjin.springbootblog.advice.exception.CustomAuthenticationEntryPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exception")
public class ExceptionApiController {

    @RequestMapping("/entrypoint")
    public ResponseEntity<?> entrypoint() {
        throw new CustomAuthenticationEntryPointException();
    }

    @RequestMapping("/accessDenied")
    public ResponseEntity<?> accessDenied() {
        throw new CustomAccessDeniedException();
    }

}
