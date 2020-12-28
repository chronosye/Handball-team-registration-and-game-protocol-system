package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.service.PlayerService;
import com.handball.system.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerControllerTest {

    @InjectMocks
    ManagerController managerController;

    @Mock
    Model model;

    @Mock
    TeamService teamService;

    @Mock
    PlayerService playerService;

    @Mock
    BindingResult bindingResult;

    private User user;
    private Team team;
    private Team team2;
    private Player player;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        team = TestData.getTeam();
        team2 = TestData.getTeam2();
        player = TestData.getPlayer();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void managerHome() {
        assertEquals("manager/manager", managerController.managerHome(user, model));
    }

    @Test
    void createTeam() throws Exception {
        when(teamService.hasManagerTeam(any(User.class))).thenReturn(false);
        assertEquals("manager/createTeam", managerController.createTeam(any(Team.class), model, user));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        when(teamService.hasManagerTeam(any())).thenReturn(true);
        mockMvc.perform(get("/manager/createTeam")).andExpect(status().isNotFound());
    }

    @Test
    void postCreateTeam() {
        when(teamService.hasManagerTeam(any(User.class))).thenReturn(false);
        assertEquals("redirect:/manager/team", managerController.createTeam(team, bindingResult, user, model));
        when(teamService.hasManagerTeam(any(User.class))).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> managerController.createTeam(team, bindingResult, user, model));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("manager/createTeam", managerController.createTeam(team, bindingResult, user, model));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("manager/createTeam", managerController.createTeam(team2, bindingResult, user, model));
    }

    @Test
    void showTeam() throws Exception {
        when(teamService.findTeamByManager(any(User.class))).thenReturn(team);
        assertEquals("manager/team", managerController.showTeam(user, model));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        when(teamService.findTeamByManager(any())).thenReturn(null);
        mockMvc.perform(get("/manager/team")).andExpect(status().isNotFound());
    }

    @Test
    void editTeam() throws Exception {
        when(teamService.findTeamByManager(any(User.class))).thenReturn(team);
        assertEquals("manager/editTeam", managerController.editTeam(user, model));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        when(teamService.findTeamByManager(any())).thenReturn(null);
        mockMvc.perform(get("/manager/team/edit")).andExpect(status().isNotFound());
    }

    @Test
    void postEditTeam() {
        assertEquals("redirect:/manager/team", managerController.editTeam(team, bindingResult));
        team.setName("");
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("manager/editTeam", managerController.editTeam(team, bindingResult));
    }

    @Test
    void deleteTeam() throws Exception {
        when(teamService.findTeamByManager(user)).thenReturn(team);
        assertEquals("redirect:/manager", managerController.deleteTeam(user));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        when(teamService.findTeamByManager(any())).thenReturn(null);
        mockMvc.perform(get("/manager/team/delete")).andExpect(status().isNotFound());
    }

    @Test
    void addPlayer() {
        assertEquals("manager/playerForm", managerController.addPlayer(model, player));
    }

    @Test
    void updatePlayer() {
        player.setId(2L);
        when(teamService.findTeamByManager(user)).thenReturn(team);
        when(playerService.findPlayerByIdAndTeam(anyLong(), any(Team.class))).thenReturn(player);
        assertEquals("manager/playerForm", managerController.updatePlayer(user, player.getId().toString(), model));
    }

    @Test
    void updatePlayer404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        when(teamService.findTeamByManager(any())).thenReturn(null);
        mockMvc.perform(get("/manager/team/editPlayer/3")).andExpect(status().isNotFound());
    }

    @Test
    void saveOrUpdatePlayer() {
        when(teamService.findTeamByManager(any())).thenReturn(team);
        assertEquals("redirect:/manager/team", managerController.saveOrUpdatePlayer(player, bindingResult, user));
        when(teamService.findTeamByManager(any())).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> managerController.saveOrUpdatePlayer(player, bindingResult, user));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("manager/playerForm", managerController.saveOrUpdatePlayer(player, bindingResult, user));
    }

    @Test
    void deletePlayer() {
        player.setId(2L);
        when(teamService.findTeamByManager(any())).thenReturn(team);
        assertEquals("redirect:/manager/team", managerController.deletePlayer(player.getId().toString(), user));
    }

    @Test
    void deletePlayer404() throws Exception {
        when(teamService.findTeamByManager(any())).thenReturn(null);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        when(teamService.findTeamByManager(any())).thenReturn(null);
        mockMvc.perform(get("/manager/team/deletePlayer/3")).andExpect(status().isNotFound());
    }
}