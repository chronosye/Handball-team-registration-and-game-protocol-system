package com.handball.system.controller;

import com.handball.system.entity.Role;
import com.handball.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPanel() {
        return "admin/admin";
    }

    @GetMapping("/editRoles")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/editRoles";
    }

    @GetMapping("/editRoles/addOrganizer/{UserId}")
    public String addOrganizer(@PathVariable String UserId) {
        userService.addRole(Long.valueOf(UserId), Role.ORGANIZER);
        return "redirect:/admin/editRoles";
    }

    @GetMapping("/editRoles/removeOrganizer/{userId}")
    public String removeOrganizer(@PathVariable String userId) {
        userService.removeRole(Long.valueOf(userId), Role.ORGANIZER);
        return "redirect:/admin/editRoles";
    }

    @GetMapping("/editRoles/addManager/{userId}")
    public String addManager(@PathVariable String userId) {
        userService.addRole(Long.valueOf(userId), Role.MANAGER);
        return "redirect:/admin/editRoles";
    }

    @GetMapping("/editRoles/removeManager/{userId}")
    public String removeManager(@PathVariable String userId) {
        userService.removeRole(Long.valueOf(userId), Role.MANAGER);
        return "redirect:/admin/editRoles";
    }

    @GetMapping("/editRoles/addProtocolist/{userId}")
    public String addProtocolist(@PathVariable String userId) {
        userService.addRole(Long.valueOf(userId), Role.PROTOCOLIST);
        return "redirect:/admin/editRoles";
    }

    @GetMapping("/editRoles/removeProtocolist/{userId}")
    public String removeProtocolist(@PathVariable String userId) {
        userService.removeRole(Long.valueOf(userId), Role.PROTOCOLIST);
        return "redirect:/admin/editRoles";
    }
}
