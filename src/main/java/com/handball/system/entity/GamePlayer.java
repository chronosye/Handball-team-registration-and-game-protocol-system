package com.handball.system.entity;

import javax.persistence.*;

@Entity
@Table(name = "game_player")
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Game game;

    private Integer shots;
    private Integer goals;

    public GamePlayer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
