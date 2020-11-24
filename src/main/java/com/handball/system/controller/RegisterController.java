package com.handball.system.controller;

import com.handball.system.entity.User;
import com.handball.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String register(User user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    String register(@Valid User user, BindingResult errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        if (userService.userExists(user.getEmail())) {
            errors.rejectValue("email", "error.user", "Lietotājs ar šādu e-pastu jau pastāv!");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
