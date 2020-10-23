package com.handball.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/login-error")
    String loginError(Model model){
        model.addAttribute("error",true);
        return "login";
    }
}
