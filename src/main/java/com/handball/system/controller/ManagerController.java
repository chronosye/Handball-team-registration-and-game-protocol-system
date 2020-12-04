package com.handball.system.controller;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.service.PlayerService;
import com.handball.system.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
    public String createTeam(Team team, Model model, @AuthenticationPrincipal User user) {
        if (teamService.hasManagerTeam(user)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
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
        if (teamService.hasManagerTeam(user)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        teamService.saveTeam(team, user);
        return "redirect:/manager/team";
    }
    //CREATING TEAM FUNCTIONALITY ENDS HERE

    //Reading created team
    @GetMapping("/team")
    public String showTeam(@AuthenticationPrincipal User user, Model model) {
        Team team = teamService.findTeamByManager(user);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("team", team);
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
    public String updatePlayer(@AuthenticationPrincipal User user, @PathVariable String playerId, Model model) {
        Team team = teamService.findTeamByManager(user);
        Player player = playerService.findPlayerByIdAndTeam(Long.valueOf(playerId), team);
        if (team == null || player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("player", player);
        return "manager/playerForm";
    }

    //saving team player to database
    @PostMapping("/team/player")
    public String saveOrUpdatePlayer(@Valid Player player, BindingResult result, @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            return "manager/playerForm";
        }
        Team team = teamService.findTeamByManager(user);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        player.setTeam(team);
        playerService.savePlayer(player);
        return "redirect:/manager/team";
    }

    //deleting team player
    @GetMapping("/team/deletePlayer/{playerId}")
    public String deletePlayer(@PathVariable String playerId, @AuthenticationPrincipal User user) {
        Team team = teamService.findTeamByManager(user);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        playerService.deletePlayerByIdAndTeam(Long.valueOf(playerId), team);
        return "redirect:/manager/team";
    }
}
