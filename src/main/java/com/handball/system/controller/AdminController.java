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
        return "admin/admin";
    }

    //FUNCTIONALITY FOR ORGANIZERS STARTS HERE
    @GetMapping("/editOrganizer")
    public String showUsers(Model model){
        model.addAttribute("users",userService.findAllUsers());
        return "admin/editOrganizer";
    }

    @GetMapping("/editOrganizer/add/{UserId}")
    public String addOrganizer(@PathVariable String UserId){
        userService.addRole(Long.valueOf(UserId),Role.ORGANIZER);
        return "redirect:/admin/editOrganizer";
    }

    @GetMapping("/editOrganizer/remove/{UserId}")
    public String removeOrganizer(@PathVariable String UserId){
        userService.removeRole(Long.valueOf(UserId),Role.ORGANIZER);
        return "redirect:/admin/editOrganizer";
    }
    //FUNCTIONALITY FOR ORGANIZERS ENDS HERE

    //FUNCTIONALITY FOR MANAGER STARTS HERE
    @GetMapping("/addManager")
    public String showManagerUsers(Model model){
        model.addAttribute("users",userService.findAllUsers());
        return "admin/addManager";
    }

    @GetMapping("/addManager/add/{UserId}")
    public String addManager(@PathVariable String UserId){
        userService.addRole(Long.valueOf(UserId),Role.MANAGER);
        return "redirect:/admin/addManager";
    }
    //FUNCTIONALITY FOR MANAGER ENDS HERE
}
