package com.handball.system.entity;

import org.springframework.validation.BindingResult;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Protocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerStats> homeTeamPlayerStats = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerStats> awayTeamPlayerStats = new ArrayList<>();

    @OneToOne
    @NotNull
    private Game game;

    public Protocol() {
    }

    public Protocol(List<Player> homeTeamPlayers, List<Player> awayTeamPlayers, Game game) {
        for (Player player : homeTeamPlayers) {
            PlayerStats gamePlayer = new PlayerStats(player, 0, 0);
            homeTeamPlayerStats.add(gamePlayer);
        }
        for (Player player : awayTeamPlayers) {
            PlayerStats gamePlayer = new PlayerStats(player, 0, 0);
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

    public void validateProtocolForm(BindingResult bindingResult) {
        Protocol protocol = this;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Protocol>> violations = validator.validate(protocol);
        for (ConstraintViolation<Protocol> violation : violations) {
            if ((!violation.getPropertyPath().toString().equals("game"))) {
                bindingResult.rejectValue(violation.getPropertyPath().toString(), "errors." + violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        int counter = 0;
        for (PlayerStats stats : homeTeamPlayerStats) {
            Set<ConstraintViolation<PlayerStats>> homeStatsViolations = validator.validate(stats);
            for (ConstraintViolation<PlayerStats> violation : homeStatsViolations) {
                if ((!violation.getPropertyPath().toString().equals("protocol"))) {
                    bindingResult.rejectValue("homeTeamPlayerStats[" + counter + "]." + violation.getPropertyPath().toString(), "errors." + "homeTeamPlayerStats[" + counter + "]." + violation.getPropertyPath().toString(), violation.getMessage());
                }
            }
            counter++;
        }
        counter = 0;
        for (PlayerStats stats : awayTeamPlayerStats) {
            Set<ConstraintViolation<PlayerStats>> awayStatsViolations = validator.validate(stats);
            for (ConstraintViolation<PlayerStats> violation : awayStatsViolations) {
                if ((!violation.getPropertyPath().toString().equals("protocol"))) {
                    bindingResult.rejectValue("awayTeamPlayerStats[" + counter + "]." + violation.getPropertyPath().toString(), "errors." + "awayTeamPlayerStats[" + counter + "]." + violation.getPropertyPath().toString(), violation.getMessage());
                }
            }
            counter++;
        }
    }
}
