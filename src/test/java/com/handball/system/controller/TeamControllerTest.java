package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.service.PlayerService;
import com.handball.system.service.PlayerStatsService;
import com.handball.system.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeamControllerTest {

    @InjectMocks
    TeamController teamController;

    @Mock
    TeamService teamService;

    @Mock
    PlayerService playerService;

    @Mock
    PlayerStatsService playerStatsService;

    @Mock
    Model model;

    private Team team;
    private Player player;

    @BeforeEach
    void setUp() {
        team = TestData.getTeam();
        player = TestData.getPlayer();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTeam() {
        when(teamService.findTeamById(anyLong())).thenReturn(team);
        assertEquals("team", teamController.getTeam("1", model));
    }

    @Test
    void getTeam404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
        when(teamService.findTeamById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/teams/3")).andExpect(status().isNotFound());
    }

    @Test
    void getTeamPlayerInfo() {
        when(teamService.findTeamById(anyLong())).thenReturn(team);
        when(playerService.findPlayerByIdAndTeam(anyLong(), any(Team.class))).thenReturn(player);
        assertEquals("player", teamController.getTeamPlayerInfo("1", "1", model));
    }

    @Test
    void getTeamPlayerInfo404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
        when(teamService.findTeamById(anyLong())).thenReturn(null);
        when(playerService.findPlayerByIdAndTeam(anyLong(), any(Team.class))).thenReturn(null);
        mockMvc.perform(get("/teams/1/player/1")).andExpect(status().isNotFound());
    }
}