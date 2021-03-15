package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.domain.validator.CreatePostValidator;
import com.rangjin.springbootblog.service.CategoryService;
import com.rangjin.springbootblog.service.PostService;
import com.rangjin.springbootblog.web.dto.CategoryResponseDto;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<CategoryResponseDto> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/")
    public String index(Model model, PageRequest pageRequest) {
        model.addAttribute("list", postService.findByStatus(pageRequest));

        return "index";
    }

    @GetMapping("/post/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("post", postService.findById(id));

        return "post/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dto", new PostRequestDto());
        return "post/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("dto") PostRequestDto dto, Errors errors, Model model) {
        new CreatePostValidator().validate(dto, errors);

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);

            return "post/create";
        }

        return "redirect:/post/" + postService.create(dto);
    }

}
