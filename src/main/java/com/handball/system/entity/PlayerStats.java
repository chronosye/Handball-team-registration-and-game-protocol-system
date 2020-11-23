package com.handball.system.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private boolean playingGame;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Protocol protocol;

    @Min(value = 0,message = "Metienu skaits nevar būt negatīvs!")
    private Integer shots;
    @Min(value = 0,message = "Vārtu skaits nevar būt negatīvs!")
    private Integer goals;

    public PlayerStats() {
    }

    public PlayerStats(Player player, Integer shots, Integer goals) {
        this.player = player;
        this.shots = shots;
        this.goals = goals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getPlayingGame() {
        return playingGame;
    }

    public void setPlayingGame(boolean playingGame) {
        this.playingGame = playingGame;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }
}
