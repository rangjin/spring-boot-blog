package com.rangjin.springbootblog.web.common;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.service.CategoryService;
import com.rangjin.springbootblog.service.PostService;
import com.rangjin.springbootblog.web.dto.CategoryResponseDto;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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

    @GetMapping("/post/category/{id}")
    public String category(@PathVariable("id") Long id, PageRequest pageRequest, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("list", postService.findByStatusAndCategory(id, pageRequest));

        return "post/category";
    }

    @GetMapping("/post/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("post", postService.findById(id));

        return "post/detail";
    }

    @GetMapping("post/create")
    public String create(Model model) {
        model.addAttribute("dto", new PostRequestDto());
        return "post/create";
    }

    @PostMapping("post/create")
    public String create(@ModelAttribute("dto") @Valid PostRequestDto dto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);

            return "post/create";
        }

        return "redirect:/post/detail/" + postService.create(dto).getId();
    }

    @GetMapping("post/edit/{id}")
    public String modify(@PathVariable("id") Long id, Model model) {
        model.addAttribute("dto", new PostRequestDto());
        model.addAttribute("post", postService.findById(id));

        return "post/edit";
    }

    @PostMapping("post/edit/{id}")
    public String modify(@PathVariable("id") Long id, @ModelAttribute("dto") @Valid PostRequestDto dto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("post", postService.findById(id));
            model.addAttribute("dto", dto);

            return "post/edit";
        }

        return "redirect:/post/detail/" + postService.modify(id, dto).getId();
    }

    @GetMapping("post/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        postService.delete(id);

        return "redirect:/";
    }

}
