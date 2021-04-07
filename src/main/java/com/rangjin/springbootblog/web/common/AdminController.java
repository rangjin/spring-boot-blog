package com.rangjin.springbootblog.web.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/register")
    public String register() {
        return "admin/register";
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

}
