package com.handball.system.entity;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Protocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerStats> homeTeamPlayerStats = new ArrayList<>();

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerStats> awayTeamPlayerStats = new ArrayList<>();

    @OneToOne
    private Game game;

    public Protocol() {
    }

    public Protocol(List<Player> homeTeamPlayers, List<Player> awayTeamPlayers,Game game) {
        for(Player player : homeTeamPlayers){
            PlayerStats gamePlayer = new PlayerStats(player,0,0);
            homeTeamPlayerStats.add(gamePlayer);
        }
        for(Player player : awayTeamPlayers){
            PlayerStats gamePlayer = new PlayerStats(player,0,0);
            awayTeamPlayerStats.add(gamePlayer);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlayerStats> getHomeTeamPlayerStats() {
        return homeTeamPlayerStats;
    }

    public void setHomeTeamPlayerStats(List<PlayerStats> homeTeamPlayerStats) {
        this.homeTeamPlayerStats = homeTeamPlayerStats;
    }

    public List<PlayerStats> getAwayTeamPlayerStats() {
        return awayTeamPlayerStats;
    }

    public void setAwayTeamPlayerStats(List<PlayerStats> awayTeamPlayerStats) {
        this.awayTeamPlayerStats = awayTeamPlayerStats;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
