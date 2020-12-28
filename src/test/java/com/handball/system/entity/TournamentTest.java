package com.handball.system.entity;

import com.handball.system.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {

    @Mock
    BindingResult bindingResult;

    private Tournament tournament;
    private Set<Team> teamSet;
    private Set<Game> games;

    @BeforeEach
    void setUp() {
        tournament = new Tournament();
        teamSet = TestData.getTeamsSet();
        games = TestData.getGames();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getId() {
        tournament.setId(11L);
        assertEquals(11L, tournament.getId());
    }

    @Test
    void getName() {
        tournament.setName("ZZ");
        assertEquals("ZZ", tournament.getName());
    }

    @Test
    void getTeams() {
        tournament.setTeams(teamSet);
        assertEquals(teamSet, tournament.getTeams());
    }

    @Test
    void getOrganizer() {
        User user = new User();
        tournament.setOrganizer(user);
        assertEquals(user, tournament.getOrganizer());
    }

    @Test
    void getGames() {
        tournament.setGames(games);
        assertEquals(games, tournament.getGames());
    }

    @Test
    void validateTournamentForm() {
        tournament.validateTournamentForm(bindingResult);
    }
}