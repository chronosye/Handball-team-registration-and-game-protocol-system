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
    public String managerHome(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("managerHasTeam",teamService.hasManagerTeam(user));
        return "manager/manager";
    }

    @GetMapping("/createTeam")
    public String createTeam(Team team,Model model){
        model.addAttribute("team",team);
        return "manager/createTeam";
    }

    @PostMapping("/createTeam")
    public String createTeam(@Valid Team team, BindingResult errors, @AuthenticationPrincipal User user){
        int index = 0;
        for(Player player : team.getPlayers()){
            if(player.getName().isBlank()){
                errors.rejectValue("players["+index+"].name","error.players["+index+"].name","Nevar būt tukšs");
            }
            if(player.getSurname().isBlank()){
                errors.rejectValue("players["+index+"].surname","error.players["+index+"].surname","Nevar būt tukšs");
            }
            if(player.getPosition().isBlank()){
                errors.rejectValue("players["+index+"].position","error.players["+index+"].position","Nevar būt tukšs");
            }
            else{
                player.setTeam(team);
            }
            index++;
        }
        if(errors.hasErrors()){
            return "manager/createTeam";
        }
        teamService.saveTeam(team,user);
        return "redirect:/manager/team";
    }

    @GetMapping("/team")
    public String showTeam(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("team",teamService.findTeamByManager(user));
        return "manager/team";
    }

    @GetMapping("/team/editPlayer/{playerId}")
    public String updatePlayer(@PathVariable String playerId,Model model){
        model.addAttribute("player",playerService.findPlayerById(Long.valueOf(playerId)));
        return "manager/editPlayer";
    }

    @PostMapping("/team/editPlayer/{playerId}")
    public String saveUpdatedPlayer(@PathVariable String playerId,@Valid Player player,BindingResult result){
        if(result.hasErrors()){
            return "manager/editPlayer";
        }
        playerService.updatePlayer(player,Long.valueOf(playerId));
        return "redirect:/manager/team";
    }

    @GetMapping("/team/deletePlayer/{playerId}")
    public String deletePlayer(@PathVariable String playerId){
        playerService.deletePlayerById(Long.valueOf(playerId));
        return "redirect:/manager/team";
    }

    @GetMapping("/team/addPlayer")
    public String addPlayer(Model model,Player player){
        model.addAttribute("player",player);
        return "manager/addPlayer";
    }

    @PostMapping("/team/addPlayer")
    public String saveAddedPlayer(@Valid Player player,BindingResult result,@AuthenticationPrincipal User user){
        if(result.hasErrors()){
            return "manager/addPlayer";
        }
        player.setTeam(teamService.findTeamByManager(user));
        playerService.savePlayer(player);
        return "redirect:/manager/team";
    }
}
