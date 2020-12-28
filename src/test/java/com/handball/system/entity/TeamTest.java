package com.handball.system.entity;

import com.handball.system.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team team;
    private Set<Tournament> tournamentSet;
    private List<Player> playerList;

    @Mock
    BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        team = new Team();
        tournamentSet = TestData.getTournaments();
        playerList = TestData.getTeamPlayers();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getId() {
        team.setId(5L);
        assertEquals(5L, team.getId());
    }

    @Test
    void getName() {
        team.setName("ABC");
        assertEquals("ABC", team.getName());
    }

    @Test
    void getManager() {
        User user = new User();
        team.setManager(user);
        assertEquals(user, team.getManager());
    }

    @Test
    void getTournaments() {
        team.setTournaments(tournamentSet);
        assertEquals(tournamentSet, team.getTournaments());
    }

    @Test
    void getPlayers() {
        team.setPlayers(playerList);
        assertEquals(playerList, team.getPlayers());
    }

    @Test
    void validateTeamForm() {
        playerList.get(1).setName(null);
        team.setPlayers(playerList);
        team.validateTeamForm(bindingResult);
    }
}