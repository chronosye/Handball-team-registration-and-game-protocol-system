package com.handball.system.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerStatsTest {

    private PlayerStats playerStats;

    @BeforeEach
    void setUp() {
        playerStats = new PlayerStats();
    }

    @Test
    void testConstructor() {
        Player player = new Player();
        PlayerStats playerStats1 = new PlayerStats(player, 10, 2);
        assertEquals(player, playerStats1.getPlayer());
        assertEquals(10, playerStats1.getShots());
        assertEquals(2, playerStats1.getGoals());
    }

    @Test
    void getId() {
        playerStats.setId(4L);
        assertEquals(4L, playerStats.getId());
    }

    @Test
    void getPlayingGame() {
        playerStats.setPlayingGame(true);
        assertTrue(playerStats.getPlayingGame());
    }

    @Test
    void getPlayer() {
        Player player = new Player();
        playerStats.setPlayer(player);
        assertEquals(player, playerStats.getPlayer());
    }

    @Test
    void getProtocol() {
        Protocol protocol = new Protocol();
        playerStats.setProtocol(protocol);
        assertEquals(protocol, playerStats.getProtocol());
    }

    @Test
    void getShots() {
        playerStats.setShots(32);
        assertEquals(32, playerStats.getShots());
    }

    @Test
    void getGoals() {
        playerStats.setGoals(40);
        assertEquals(40, playerStats.getGoals());
    }
}