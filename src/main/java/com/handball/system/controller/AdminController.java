package com.handball.system.controller;

import com.handball.system.entity.Role;
import com.handball.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPanel(){
        return "admin";
    }

    @GetMapping("/editOrganizer")
    public String showUsers(Model model){
        model.addAttribute("users",userService.findAllUsers());
        return "editOrganizer";
    }

    @GetMapping("/editOrganizer/add/{id}")
    public String addOrganizer(@PathVariable String id){
        userService.addRole(Long.valueOf(id),Role.ORGANIZER);
        return "redirect:/admin/editOrganizer";
    }

    @GetMapping("/editOrganizer/remove/{id}")
    public String removeOrganizer(@PathVariable String id){
        userService.removeRole(Long.valueOf(id),Role.ORGANIZER);
        return "redirect:/admin/editOrganizer";
    }
}
