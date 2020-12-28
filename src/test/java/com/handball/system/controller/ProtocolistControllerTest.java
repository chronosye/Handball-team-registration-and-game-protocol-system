package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.Game;
import com.handball.system.entity.Protocol;
import com.handball.system.entity.User;
import com.handball.system.service.GameService;
import com.handball.system.service.PlayerService;
import com.handball.system.service.PlayerStatsService;
import com.handball.system.service.ProtocolService;
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

class ProtocolistControllerTest {

    @InjectMocks
    ProtocolistController protocolistController;

    @Mock
    Model model;

    @Mock
    GameService gameService;

    @Mock
    PlayerService playerService;

    @Mock
    PlayerStatsService playerStatsService;

    @Mock
    ProtocolService protocolService;

    @Mock
    BindingResult bindingResult;

    private User user;
    private Game game;
    private Protocol protocol;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        game = TestData.getGame();
        protocol = TestData.getProtocol();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void managerHome() {
        assertEquals("protocolist/protocolist", protocolistController.protocolistHome(user, model));
    }

    @Test
    void addProtocol() {
        when(gameService.findGameByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(game);
        assertEquals("protocolist/protocolForm", protocolistController.addProtocol(game.getId().toString(), model, user));
    }

    @Test
    void addProtocol404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(protocolistController).build();
        when(gameService.findGameByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/protocolist/game/3/addProtocol")).andExpect(status().isNotFound());
    }

    @Test
    void editProtocol() {
        when(gameService.findGameByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(game);
        assertEquals("protocolist/protocolForm", protocolistController.editProtocol(game.getId().toString(), model, user));
    }

    @Test
    void editProtocol404() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(protocolistController).build();
        when(gameService.findGameByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(null);
        mockMvc.perform(get("/protocolist/game/3/editProtocol")).andExpect(status().isNotFound());
    }

    @Test
    void protocol() {
        when(gameService.findGameByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(game);
        assertEquals("redirect:/protocolist", protocolistController.Protocol(game.getId().toString(), protocol, bindingResult, model, user));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("protocolist/protocolForm", protocolistController.Protocol(game.getId().toString(), protocol, bindingResult, model, user));
        when(gameService.findGameByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> protocolistController.Protocol("1", protocol, bindingResult, model, user));
    }
}