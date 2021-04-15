package com.rangjin.springbootblog.web.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PostController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/post/category/{id}")
    public String category(@PathVariable("id") Long id) {
        return "post/category";
    }

    @GetMapping("/post/detail/{id}")
    public String detail(@PathVariable("id") Long id) {
        return "post/detail";
    }

    @GetMapping("post/create")
    public String create() {
        return "post/create";
    }

    @GetMapping("post/edit/{id}")
    public String modify(@PathVariable("id") Long id) {
        return "post/edit";
    }

    @GetMapping("error")
    public String error() {
        return "error";
    }

}
