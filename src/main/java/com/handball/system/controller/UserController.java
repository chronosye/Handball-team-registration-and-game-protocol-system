package com.handball.system.controller;

import com.handball.system.entity.User;
import com.handball.system.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/profile/editData")
    public String editProfileData(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "user/dataForm";
    }

    @PostMapping("/profile/editData")
    public String editProfileData(@Valid User formUser, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (userService.userExists(formUser.getEmail()) && !formUser.getEmail().equals(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Lietotājs ar šādu e-pastu jau pastāv!");
        }
        if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname") || bindingResult.hasFieldErrors("email")) {
            return "user/dataForm";
        }
        userService.updateUserData(formUser, user);
        return "redirect:/profile";
    }

    @GetMapping("/profile/editPassword")
    public String editProfilePassword(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "user/passwordForm";
    }

    @PostMapping("/profile/editPassword")
    public String editProfilePassword(Model model, @AuthenticationPrincipal User user, @RequestParam String oldPassword,
                                      @RequestParam String newPassword, @RequestParam String newPasswordRepeat) {
        if (userService.validatePasswordChange(user, oldPassword, newPassword, newPasswordRepeat, model)) {
            return "user/passwordForm";
        }
        userService.updateUserPassword(user, newPassword);
        return "redirect:/profile";
    }
}
