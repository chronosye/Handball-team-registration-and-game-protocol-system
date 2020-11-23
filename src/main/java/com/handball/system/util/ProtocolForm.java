package com.handball.system.util;

import com.handball.system.entity.PlayerStats;
import com.handball.system.entity.Player;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class ProtocolForm {

    @Valid
    private List<PlayerStats> homeTeamGamePlayer = new ArrayList<>();

    @Valid
    private List<PlayerStats> awayTeamGamePlayer = new ArrayList<>();

    public ProtocolForm() {
    }

    public ProtocolForm(List<Player> homeTeamPlayers, List<Player> awayTeamPlayers) {
        for(Player player : homeTeamPlayers){
            PlayerStats gamePlayer = new PlayerStats(player,0,0);
            homeTeamGamePlayer.add(gamePlayer);
        }
        for(Player player : awayTeamPlayers){
            PlayerStats gamePlayer = new PlayerStats(player,0,0);
            awayTeamGamePlayer.add(gamePlayer);
        }
    }

    public List<PlayerStats> getHomeTeamGamePlayer() {
        return homeTeamGamePlayer;
    }

    public void setHomeTeamGamePlayer(List<PlayerStats> homeTeamGamePlayer) {
        this.homeTeamGamePlayer = homeTeamGamePlayer;
    }

    public List<PlayerStats> getAwayTeamGamePlayer() {
        return awayTeamGamePlayer;
    }

    public void setAwayTeamGamePlayer(List<PlayerStats> awayTeamGamePlayer) {
        this.awayTeamGamePlayer = awayTeamGamePlayer;
    }
}
