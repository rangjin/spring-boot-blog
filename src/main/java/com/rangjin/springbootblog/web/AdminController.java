package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.domain.validator.RegisterAdminValidator;
import com.rangjin.springbootblog.service.AdminService;
import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("dto", new AdminRequestDto());

        return "admin/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("dto") AdminRequestDto dto, Errors errors, Model model) {
        new RegisterAdminValidator().validate(dto, errors);

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);

            return "admin/register";
        }

        adminService.register(dto);

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(Model model) {
        return "admin/login";
    }

}
