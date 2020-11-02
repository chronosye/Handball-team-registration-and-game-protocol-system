package com.handball.system.controller;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.service.TeamService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final TeamService teamService;

    public ManagerController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("")
    public String managerHome(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("managerHasTeam",teamService.hasManagerTeam(user));
        return "manager";
    }

    @GetMapping("/createTeam")
    public String createTeam(Team team,Model model){
        model.addAttribute("team",team);
        return "createTeam";
    }

    @PostMapping("/createTeam")
    public String createTeam(@Valid Team team, BindingResult errors, @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "createTeam";
        }
        for(Player player : team.getPlayers()){
            player.setTeam(team);
        }
        teamService.saveTeam(team,user);
        return "redirect:/";
    }
}
