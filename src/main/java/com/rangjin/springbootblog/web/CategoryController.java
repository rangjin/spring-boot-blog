package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.domain.validator.CategoryValidator;
import com.rangjin.springbootblog.service.CategoryService;
import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());

        return "category/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dto", new CategoryRequestDto());

        return "category/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("dto") CategoryRequestDto dto, Errors errors, Model model) {
        new CategoryValidator().validate(dto, errors);

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);

            return "category/edit";
        }

        categoryService.create(dto);

        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String modify(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("dto", new CategoryRequestDto());

        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String modify(@PathVariable("id") Long id, @ModelAttribute("dto") CategoryRequestDto dto, Errors errors, Model model) {
        new CategoryValidator().validate(dto, errors);

        if (errors.hasErrors()) {
            model.addAttribute("category", categoryService.findById(id));
            model.addAttribute("dto", dto);

            return "category/edit";
        }

        categoryService.modify(id, dto);

        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        categoryService.delete(id);

        return "redirect:/category";
    }

}
