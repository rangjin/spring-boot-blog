package com.rangjin.springbootblog.service;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof AuthenticationServiceException) {
            httpServletRequest.setAttribute("usernameErrorMsg", "존재하지 않는 사용자입니다.");
        } else if(e instanceof BadCredentialsException) {
            httpServletRequest.setAttribute("username", httpServletRequest.getParameter("username"));
            httpServletRequest.setAttribute("passwordErrorMsg", "비밀번호가 일치하지 않습니다.");
        }

        // 로그인 페이지로 다시 포워딩
        httpServletRequest.getRequestDispatcher("/admin/login?error=true").forward(httpServletRequest, httpServletResponse);
    }

}
