package com.handball.system.controller;

import com.handball.system.entity.Role;
import com.handball.system.entity.User;
import com.handball.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

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

    @GetMapping("/addOrganizer")
    public String showUsers(Model model){
        model.addAttribute("users",userService.findAllUsers());
        return "addOrganizer";
    }

    @GetMapping("/addOrganizer/{id}")
    public String addOrganizer(@PathVariable String id){
        User user = userService.findUser(Long.valueOf(id));
        Set<Role> roles = user.getRoles();
        roles.add(Role.ORGANIZER);
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin/addOrganizer";
    }
}
