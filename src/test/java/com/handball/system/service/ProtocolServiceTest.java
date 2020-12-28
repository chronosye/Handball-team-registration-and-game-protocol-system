package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.Game;
import com.handball.system.entity.PlayerStats;
import com.handball.system.entity.Protocol;
import com.handball.system.repository.PlayerStatsRepository;
import com.handball.system.repository.ProtocolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class ProtocolServiceTest {

    @InjectMocks
    ProtocolService protocolService;

    @Mock
    ProtocolRepository protocolRepository;

    @Mock
    PlayerStatsRepository playerStatsRepository;

    private Game game;
    private Protocol protocol;
    private List<PlayerStats> playerStatsList;

    @BeforeEach
    void setUp() {
        game = TestData.getGame();
        protocol = TestData.getProtocol();
        playerStatsList = TestData.getPlayerStats();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getProtocolByGame() {
        when(protocolRepository.findProtocolByGame(any(Game.class))).thenReturn(protocol);
        protocolService.getProtocolByGame(game);
        verify(protocolRepository, atLeastOnce()).findProtocolByGame(any(Game.class));
    }

    @Test
    void getProtocolToEdit() {
        protocol.setHomeTeamPlayerStats(playerStatsList);
        protocol.setAwayTeamPlayerStats(playerStatsList);
        when(protocolRepository.findProtocolByGame(any(Game.class))).thenReturn(protocol);
        protocolService.getProtocolToEdit(game, protocol);
        verify(protocolRepository, atLeastOnce()).findProtocolByGame(any(Game.class));
    }

    @Test
    void saveProtocol() {
        protocol.setHomeTeamPlayerStats(playerStatsList);
        protocol.setAwayTeamPlayerStats(playerStatsList);
        protocolService.saveProtocol(protocol, game);
        verify(protocolRepository, atLeastOnce()).save(any(Protocol.class));
        protocol.setId(null);
        protocolService.saveProtocol(protocol, game);
        verify(protocolRepository, atLeastOnce()).save(any(Protocol.class));
    }
}