package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.Player;
import com.handball.system.entity.PlayerStats;
import com.handball.system.repository.PlayerStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerStatsServiceTest {

    @InjectMocks
    PlayerStatsService playerStatsService;

    @Mock
    PlayerStatsRepository playerStatsRepository;

    @Mock
    BindingResult bindingResult;

    private List<PlayerStats> playerStatsList;
    private Player player;

    @BeforeEach
    void setUp() {
        playerStatsList = TestData.getPlayerStats();
        player = TestData.getPlayer();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findPlayerStats() {
        when(playerStatsRepository.findAllByPlayer(any(Player.class))).thenReturn(playerStatsList);
        playerStatsService.findPlayerStats(player);
        verify(playerStatsRepository, atLeastOnce()).findAllByPlayer(any(Player.class));
    }

    @Test
    void getPlayerTotalShots() {
        assertEquals(28, playerStatsService.getPlayerTotalShots(playerStatsList));
    }

    @Test
    void getPlayerTotalGoals() {
        assertEquals(21, playerStatsService.getPlayerTotalGoals(playerStatsList));
    }

    @Test
    void validatePlayerStats() {
        playerStatsList.get(1).setGoals(10);
        playerStatsService.validatePlayerStats(playerStatsList, bindingResult, true);
        playerStatsService.validatePlayerStats(playerStatsList, bindingResult, false);
        playerStatsList.remove(1);
        playerStatsList.remove(2);
        playerStatsService.validatePlayerStats(playerStatsList, bindingResult, true);
        playerStatsService.validatePlayerStats(playerStatsList, bindingResult, false);
    }
}