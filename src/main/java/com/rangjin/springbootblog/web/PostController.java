package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.service.PostService;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/create")
    public String newPost(Model model) {
        model.addAttribute("dto", new PostRequestDto());
        return "newPost";
    }

    @PostMapping("/create")
    public String newPost(@ModelAttribute("dto")PostRequestDto dto, Model model) {
        return "redirect:/post/" + postService.create(dto);
    }

}
