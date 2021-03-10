package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", postService.findByStatus());

        return "index";
    }

}
