package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.Game;
import com.handball.system.entity.Protocol;
import com.handball.system.entity.Tournament;
import com.handball.system.service.GameService;
import com.handball.system.service.ProtocolService;
import com.handball.system.service.TournamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TournamentControllerTest {

    @InjectMocks
    TournamentController tournamentController;

    @Mock
    TournamentService tournamentService;

    @Mock
    GameService gameService;

    @Mock
    ProtocolService protocolService;

    @Mock
    Model model;

    private Tournament tournament;
    private Game game;
    private Protocol protocol;

    @BeforeEach
    void setUp() {
        tournament = TestData.getTournament();
        game = TestData.getGame();
        protocol = TestData.getProtocol();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void tournaments() {
        assertEquals("tournaments", tournamentController.tournaments(model));
    }

    @Test
    void tournament() {
        when(tournamentService.findTournamentById(anyLong())).thenReturn(tournament);
        assertEquals("tournament", tournamentController.tournament(model, "1"));
    }

    @Test
    void tournament404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tournamentController).build();
        when(tournamentService.findTournamentById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/tournaments/3")).andExpect(status().isNotFound());
    }

    @Test
    void tournamentGameInfo() {
        when(gameService.findGameByIdAndTournament(anyLong(), any())).thenReturn(game);
        when(protocolService.getProtocolByGame(any())).thenReturn(protocol);
        assertEquals("game", tournamentController.tournamentGameInfo(model, "1", "1"));
    }

    @Test
    void tournamentGameInfo404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tournamentController).build();
        when(gameService.findGameByIdAndTournament(anyLong(), any())).thenReturn(null);
        when(protocolService.getProtocolByGame(any())).thenReturn(null);
        mockMvc.perform(get("/tournaments/1/game/1")).andExpect(status().isNotFound());
    }
}