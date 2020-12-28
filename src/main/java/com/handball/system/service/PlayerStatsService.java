package com.handball.system.service;

import com.handball.system.entity.Player;
import com.handball.system.entity.PlayerStats;
import com.handball.system.repository.PlayerStatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;

    public PlayerStatsService(PlayerStatsRepository playerStatsRepository) {
        this.playerStatsRepository = playerStatsRepository;
    }

    public List<PlayerStats> findPlayerStats(Player player) {
        return playerStatsRepository.findAllByPlayer(player);
    }

    public int getPlayerTotalShots(List<PlayerStats> playerStats) {
        int shots = 0;
        for (PlayerStats stat : playerStats) {
            shots = shots + stat.getShots();
        }
        return shots;
    }

    public int getPlayerTotalGoals(List<PlayerStats> playerStats) {
        int goals = 0;
        for (PlayerStats stat : playerStats) {
            goals = goals + stat.getGoals();
        }
        return goals;
    }

    public void validatePlayerStats(List<PlayerStats> playerStatsList, BindingResult bindingResult, boolean isHomeTeam) {
        int counter = 0;
        Set<PlayerStats> validPlayers = new HashSet<>();
        for (PlayerStats gamePlayer : playerStatsList) {
            if (gamePlayer.getPlayingGame()) {
                if (gamePlayer.getShots() < gamePlayer.getGoals()) {
                    if (isHomeTeam) {
                        bindingResult.rejectValue("homeTeamPlayerStats[" + counter + "].shots", "error.homeTeamPlayerStats[" + counter + "].shots", "Metieni nevar būt mazāk par iemestajiem vārtiem");
                    } else {
                        bindingResult.rejectValue("awayTeamPlayerStats[" + counter + "].shots", "error.awayTeamPlayerStats[" + counter + "].shots", "Metieni nevar būt mazāk par iemestajiem vārtiem");
                    }
                } else {
                    validPlayers.add(gamePlayer);
                }
            }
            counter++;
        }
        if (validPlayers.size() < 6) {
            if (isHomeTeam) {
                bindingResult.rejectValue("homeTeamPlayerStats", "error.homeTeamPlayerStats", "Mājas spēlējošo spēlētāju skaits nevar būt mazāks par 6!");
            } else {
                bindingResult.rejectValue("awayTeamPlayerStats", "error.awayTeamPlayerStats", "Viesu spēlējošo spēlētāju skaits nevar būt mazāks par 6!");
            }
        }
    }
}
