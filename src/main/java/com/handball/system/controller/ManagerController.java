package com.handball.system.controller;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.service.PlayerService;
import com.handball.system.service.TeamService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public ManagerController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("")
    public String managerHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("managerHasTeam", teamService.hasManagerTeam(user));
        return "manager/manager";
    }

    //CREATING TEAM FUNCTIONALITY STARS HERE
    @GetMapping("/createTeam")
    public String createTeam(Team team, Model model) {
        model.addAttribute("team", team);
        model.addAttribute("size", 5);
        return "manager/createTeam";
    }

    @PostMapping("/createTeam")
    public String createTeam(@Valid Team team, BindingResult errors, @AuthenticationPrincipal User user, Model model) {
        if (errors.hasErrors()) {
            if (team.getPlayers().size() <= 6) {
                model.addAttribute("size", 5);
            } else {
                model.addAttribute("size", team.getPlayers().size() - 1);
            }
            return "manager/createTeam";
        }
        teamService.saveTeam(team, user);
        return "redirect:/manager/team";
    }
    //CREATING TEAM FUNCTIONALITY ENDS HERE

    //Reading created team
    @GetMapping("/team")
    public String showTeam(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("team", teamService.findTeamByManager(user));
        return "manager/team";
    }

    //Creating new team player
    @GetMapping("/team/addPlayer")
    public String addPlayer(Model model, Player player) {
        model.addAttribute("player", player);
        return "manager/playerForm";
    }

    //Editing team player
    @GetMapping("/team/editPlayer/{playerId}")
    public String updatePlayer(@PathVariable String playerId, Model model) {
        model.addAttribute("player", playerService.findPlayerById(Long.valueOf(playerId)));
        return "manager/playerForm";
    }

    //saving team player to database
    @PostMapping("/team/player")
    public String saveOrUpdatePlayer(@Valid Player player, BindingResult result, @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            return "manager/playerForm";
        }
        player.setTeam(teamService.findTeamByManager(user));
        playerService.savePlayer(player);
        return "redirect:/manager/team";
    }

    //deleting team player
    @GetMapping("/team/deletePlayer/{playerId}")
    public String deletePlayer(@PathVariable String playerId) {
        playerService.deletePlayerById(Long.valueOf(playerId));
        return "redirect:/manager/team";
    }
}
