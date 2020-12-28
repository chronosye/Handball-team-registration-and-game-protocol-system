package com.handball.system.entity;

import com.handball.system.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProtocolTest {

    @Mock
    BindingResult bindingResult;

    private Protocol protocol;
    private List<PlayerStats> playerStatsList;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        protocol = new Protocol();
        players = TestData.getTeamPlayers();
        playerStatsList = TestData.getPlayerStats();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testConstructor() {
        Protocol constProtocol = new Protocol(players, players);
        assertEquals(players.size(), constProtocol.getHomeTeamPlayerStats().size());
        assertEquals(players.size(), constProtocol.getAwayTeamPlayerStats().size());
    }

    @Test
    void getId() {
        protocol.setId(3L);
        assertEquals(3L, protocol.getId());
    }

    @Test
    void getHomeTeamPlayerStats() {
        protocol.setHomeTeamPlayerStats(playerStatsList);
        assertEquals(playerStatsList, protocol.getHomeTeamPlayerStats());
    }

    @Test
    void getAwayTeamPlayerStats() {
        protocol.setAwayTeamPlayerStats(playerStatsList);
        assertEquals(playerStatsList, protocol.getAwayTeamPlayerStats());
    }

    @Test
    void getGame() {
        Game game = new Game();
        protocol.setGame(game);
        assertEquals(game, protocol.getGame());
    }

    @Test
    void validateProtocolForm() {
        playerStatsList.get(1).setGoals(null);
        protocol.setHomeTeamPlayerStats(playerStatsList);
        protocol.setAwayTeamPlayerStats(playerStatsList);
        protocol.validateProtocolForm(bindingResult);
    }
}