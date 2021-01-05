package com.handball.system.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GameTest {

    @Mock
    BindingResult bindingResult;

    private Game game;
    private Tournament tournament;

    @BeforeEach
    void setUp() {
        game = new Game();
        tournament = new Tournament();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getId() {
        game.setId(2L);
        assertEquals(2L, game.getId());
    }

    @Test
    void getTournament() {
        game.setTournament(tournament);
        assertEquals(tournament, game.getTournament());
    }

    @Test
    void getDate() {
        Date date = new Date();
        game.setDate(date);
        assertEquals(date, game.getDate());
    }

    @Test
    void getEnded() {
        game.setEnded(true);
        assertTrue(game.getEnded());
    }

    @Test
    void getHomeTeam() {
        Team team = new Team();
        game.setHomeTeam(team);
        assertEquals(team, game.getHomeTeam());
    }

    @Test
    void getAwayTeam() {
        Team team = new Team();
        game.setAwayTeam(team);
        assertEquals(team, game.getAwayTeam());
    }

    @Test
    void getHomeTeamGoals() {
        game.setHomeTeamGoals(12);
        assertEquals(12, game.getHomeTeamGoals());
    }

    @Test
    void getAwayTeamGoals() {
        game.setAwayTeamGoals(33);
        assertEquals(33, game.getAwayTeamGoals());
    }

    @Test
    void getProtocolist() {
        User user = new User();
        game.setProtocolist(user);
        assertEquals(user, game.getProtocolist());
    }

    @Test
    void getProtocol() {
        Protocol protocol = new Protocol();
        game.setProtocol(protocol);
        assertEquals(protocol, game.getProtocol());
    }

    @Test
    void validateGameForm() {
        Team team = new Team();
        game.setHomeTeam(team);
        game.setAwayTeam(team);
        when(bindingResult.hasErrors()).thenReturn(true);
        game.validateGameForm(bindingResult);
    }
}