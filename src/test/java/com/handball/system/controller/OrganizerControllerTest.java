package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.Game;
import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.service.GameService;
import com.handball.system.service.TeamService;
import com.handball.system.service.TournamentService;
import com.handball.system.service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrganizerControllerTest {

    @InjectMocks
    OrganizerController organizerController;

    @Mock
    TournamentService tournamentService;

    @Mock
    TeamService teamService;

    @Mock
    GameService gameService;

    @Mock
    UserService userService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    private User user;
    private Tournament tournament;
    private Team team;
    private Game game;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        tournament = TestData.getTournament();
        team = TestData.getTeam();
        game = TestData.getGame();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void organizerPanel() {
        assertEquals("organizer/organizer", organizerController.organizerPanel());
    }

    @Test
    void getOrganizerTournaments() {
        assertEquals("organizer/tournaments", organizerController.getOrganizerTournaments(model, user));
    }

    @Test
    void getTournament() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("organizer/tournament", organizerController.getTournament(tournament.getId().toString(), model, user));
    }

    @Test
    void getTournament404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/3")).andExpect(status().isNotFound());
    }

    @Test
    void showTeamsToEdit() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("organizer/showTeams", organizerController.showTeamsToEdit(tournament.getId().toString(), model, user));
    }

    @Test
    void showTeamsToEdit404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/3/editTeams")).andExpect(status().isNotFound());
    }

    @Test
    void addTeamToTournament() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        when(teamService.findTeamById(anyLong())).thenReturn(team);
        assertEquals("redirect:/organizer/tournaments/" + tournament.getId().toString() + "/editTeams", organizerController.addTeamToTournament(tournament.getId().toString(), team.getId().toString(), user));
    }

    @Test
    void addTeamToTournament404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        when(teamService.findTeamById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/3/editTeams/1/add")).andExpect(status().isNotFound());
    }

    @Test
    void removeTeamFromTournament() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        when(teamService.findTeamById(anyLong())).thenReturn(team);
        assertEquals("redirect:/organizer/tournaments/" + tournament.getId().toString() + "/editTeams", organizerController.removeTeamFromTournament(tournament.getId().toString(), team.getId().toString(), user));
    }

    @Test
    void removeTeamFromTournament404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        when(teamService.findTeamById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/3/editTeams/1/remove")).andExpect(status().isNotFound());
    }

    @Test
    void createTournament() {
        assertEquals("organizer/tournamentForm", organizerController.createTournament(tournament, model));
    }

    @Test
    void editTournament() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("organizer/tournamentForm", organizerController.editTournament(model, tournament.getId().toString(), user));
    }

    @Test
    void editTournament404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/3/edit")).andExpect(status().isNotFound());
    }

    @Test
    void createOrUpdateTournament() {
        when(tournamentService.saveOrUpdateTournament(tournament, user)).thenReturn(tournament);
        assertEquals("redirect:/organizer/tournaments/" + tournament.getId(), organizerController.createOrUpdateTournament(tournament, bindingResult, user));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("organizer/tournamentForm", organizerController.createOrUpdateTournament(tournament, bindingResult, user));
    }

    @Test
    void createTournamentGame() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("organizer/gameForm", organizerController.createTournamentGame(tournament.getId().toString(), model, game, user));
    }

    @Test
    void createTournamentGame404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/3/createGame")).andExpect(status().isNotFound());
    }

    @Test
    void saveOrUpdateTournamentGame() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("redirect:/organizer/tournaments/" + tournament.getId(), organizerController.saveOrUpdateTournamentGame(game, bindingResult, tournament.getId().toString(), model, user));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("organizer/gameForm", organizerController.saveOrUpdateTournamentGame(game, bindingResult, tournament.getId().toString(), model, user));
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> organizerController.saveOrUpdateTournamentGame(game, bindingResult, "1", model, user));
    }

    @Test
    void editGame() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        when(gameService.findGameByIdAndTournament(anyLong(), any(Tournament.class))).thenReturn(game);
        assertEquals("organizer/gameForm", organizerController.editGame(tournament.getId().toString(), game.getId().toString(), model, user));
    }

    @Test
    void editGame404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        when(gameService.findGameByIdAndTournament(anyLong(), any(Tournament.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/1/game/1/edit")).andExpect(status().isNotFound());
    }

    @Test
    void deleteGame() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("redirect:/organizer/tournaments/" + tournament.getId(), organizerController.deleteGame(tournament.getId().toString(), game.getId().toString(), user));
    }

    @Test
    void deleteGame404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/1/game/1/delete")).andExpect(status().isNotFound());
    }

    @Test
    void deleteTournament() {
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        assertEquals("redirect:/organizer/tournaments", organizerController.deleteTournament(tournament.getId().toString(), user));
    }

    @Test
    void deleteTournament404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        when(tournamentService.findTournamentByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/organizer/tournaments/1/delete")).andExpect(status().isNotFound());
    }
}