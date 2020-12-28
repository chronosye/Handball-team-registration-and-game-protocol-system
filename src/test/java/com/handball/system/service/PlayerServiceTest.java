package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    private Player player;
    private Team team;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        team = TestData.getTeam();
        player = TestData.getPlayer();
        player.setId(1L);
        players = TestData.getTeamPlayers();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findPlayerByIdAndTeam() {
        when(playerRepository.findByIdAndTeam(anyLong(), any(Team.class))).thenReturn(player);
        playerService.findPlayerByIdAndTeam(player.getId(), team);
        verify(playerRepository, atLeastOnce()).findByIdAndTeam(anyLong(), any(Team.class));
    }

    @Test
    void savePlayer() {
        playerService.savePlayer(player);
        verify(playerRepository, atLeastOnce()).save(any(Player.class));
    }

    @Test
    void findPlayersByTeam() {
        when(playerRepository.findPlayersByTeam(any(Team.class))).thenReturn(players);
        playerService.findPlayersByTeam(team);
        verify(playerRepository, atLeastOnce()).findPlayersByTeam(any(Team.class));
    }

    @Test
    void deletePlayerByIdAndTeam() {
        when(playerRepository.findPlayersByTeam(any(Team.class))).thenReturn(players);
        when(playerRepository.findByIdAndTeam(anyLong(), any(Team.class))).thenReturn(player);
        playerService.deletePlayerByIdAndTeam(anyLong(), team);
        verify(playerRepository, atLeastOnce()).findPlayersByTeam(any(Team.class));
        verify(playerRepository, atLeastOnce()).findByIdAndTeam(anyLong(), any(Team.class));
        verify(playerRepository, atLeastOnce()).delete(any(Player.class));
    }

    @Test
    void deletePlayerByIdAndTeamException() {
        players.remove(1);
        players.remove(2);
        when(playerRepository.findPlayersByTeam(any(Team.class))).thenReturn(players);
        assertThrows(ResponseStatusException.class, () -> playerService.deletePlayerByIdAndTeam(player.getId(), team));
    }
}