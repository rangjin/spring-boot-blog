package com.rangjin.springbootblog.web.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping("")
    public String categories() {
        return "category/list";
    }

    @GetMapping("/create")
    public String create() {
        return "category/create";
    }

    @GetMapping("/edit/{id}")
    public String modify(@PathVariable("id") Long id) {
        return "category/edit";
    }

}
