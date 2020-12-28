package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.Game;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.repository.GameRepository;
import com.handball.system.repository.ProtocolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.Mockito.*;

class GameServiceTest {

    @InjectMocks
    GameService gameService;

    @Mock
    GameRepository gameRepository;

    @Mock
    ProtocolRepository protocolRepository;

    private Game game;
    private Tournament tournament;
    private User protocolist;
    private Set<Game> games;

    @BeforeEach
    void setUp() {
        game = TestData.getGame();
        tournament = TestData.getTournament();
        protocolist = TestData.getUser();
        games = TestData.getGames();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveGame() {
        gameService.saveGame(game, tournament);
        verify(protocolRepository, atLeastOnce()).deleteByGame(any(Game.class));
        verify(gameRepository, atLeastOnce()).save(any(Game.class));
    }

    @Test
    void findGameByIdAndTournament() {
        when(gameRepository.findByIdAndTournament(anyLong(), any(Tournament.class))).thenReturn(game);
        gameService.findGameByIdAndTournament(game.getId(), tournament);
        verify(gameRepository, atLeastOnce()).findByIdAndTournament(anyLong(), any(Tournament.class));
    }

    @Test
    void findGameByIdAndProtocolist() {
        when(gameRepository.findByIdAndProtocolist(anyLong(), any(User.class))).thenReturn(game);
        gameService.findGameByIdAndProtocolist(game.getId(), protocolist);
        verify(gameRepository, atLeastOnce()).findByIdAndProtocolist(anyLong(), any(User.class));
    }

    @Test
    void findTeamsInGames() {
        when(gameRepository.findAllByTournament(any(Tournament.class))).thenReturn(games);
        gameService.findTeamsInGames(tournament);
        verify(gameRepository, atLeastOnce()).findAllByTournament(any(Tournament.class));
    }

    @Test
    void findGamesByProtocolist() {
        when(gameRepository.findAllByProtocolist(any(User.class))).thenReturn(games);
        gameService.findGamesByProtocolist(protocolist);
        verify(gameRepository, atLeastOnce()).findAllByProtocolist(any(User.class));
    }

    @Test
    void deleteGameByIdAndTournament() {
        gameService.deleteGameByIdAndTournament(game.getId(), tournament);
        verify(gameRepository, atLeastOnce()).deleteGameByIdAndTournament(anyLong(), any(Tournament.class));
    }
}