package com.handball.system.controller;

import com.handball.system.entity.Player;
import com.handball.system.entity.PlayerStats;
import com.handball.system.service.PlayerService;
import com.handball.system.service.PlayerStatsService;
import com.handball.system.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;
    private final PlayerStatsService playerStatsService;

    public TeamController(TeamService teamService, PlayerService playerService, PlayerStatsService playerStatsService) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.playerStatsService = playerStatsService;
    }

    @GetMapping("/{teamId}")
    public String getTeam(@PathVariable String teamId, Model model) {
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(teamId)));
        return "team";
    }

    @GetMapping("/{teamId}/player/{playerId}")
    public String getTeamPlayerInfo(@PathVariable String teamId, @PathVariable String playerId, Model model) {
        Player player = playerService.findPlayerById(Long.valueOf(playerId));
        List<PlayerStats> playerStats = playerStatsService.findPlayerStats(player);
        model.addAttribute("player", player);
        model.addAttribute("playerStats", playerStats);
        model.addAttribute("totalShots", playerStatsService.getPlayerTotalShots(playerStats));
        model.addAttribute("totalGoals", playerStatsService.getPlayerTotalGoals(playerStats));
        model.addAttribute("teamId", teamId);
        return "player";
    }
}