package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Model model, PageRequest pageRequest) {
        model.addAttribute("list", postService.findByStatus(pageRequest));

        return "index";
    }

    @GetMapping("/post/{id}")
    public String postDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("post", postService.findById(id));

        return "post";
    }

}
