package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.Game;
import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.repository.GameRepository;
import com.handball.system.repository.TeamRepository;
import com.handball.system.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

class TeamServiceTest {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    TournamentRepository tournamentRepository;

    private User user;
    private Team team;
    private List<Team> teams;
    private Set<Game> games;
    private Set<Tournament> tournaments;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        team = TestData.getTeam();
        teams = TestData.getTeams();
        games = TestData.getGames();
        tournaments = TestData.getTournaments();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void hasManagerTeam() {
        when(teamRepository.findByManager(any(User.class))).thenReturn(team);
        teamService.hasManagerTeam(user);
        verify(teamRepository, atLeastOnce()).findByManager(any(User.class));
    }

    @Test
    void findAllTeams() {
        when(teamRepository.findAll()).thenReturn(teams);
        teamService.findAllTeams();
        verify(teamRepository, atLeastOnce()).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void findTeamById() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        teamService.findTeamById(team.getId());
        verify(teamRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void findTeamByManager() {
        when(teamRepository.findByManager(any(User.class))).thenReturn(team);
        teamService.findTeamByManager(user);
        verify(teamRepository, atLeastOnce()).findByManager(any(User.class));
    }

    @Test
    void updateTeam() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        teamService.updateTeam(team);
        verify(teamRepository, atLeastOnce()).findById(anyLong());
        verify(teamRepository, atLeastOnce()).save(any(Team.class));
    }

    @Test
    void saveTeam() {
        when(teamRepository.findByManager(any(User.class))).thenReturn(null);
        teamService.saveTeam(team, user);
        verify(teamRepository, atLeastOnce()).findByManager(any(User.class));
    }

    @Test
    void deleteTeam() {
        when(gameRepository.findAllByHomeTeamOrAwayTeam(any(Team.class), any(Team.class))).thenReturn(games);
        when(tournamentRepository.findAllByTeamsContaining(any(Team.class))).thenReturn(tournaments);
        teamService.deleteTeam(team);
        verify(gameRepository, atLeastOnce()).findAllByHomeTeamOrAwayTeam(any(Team.class), any(Team.class));
        verify(gameRepository, atLeastOnce()).delete(any(Game.class));
        verify(tournamentRepository, atLeastOnce()).findAllByTeamsContaining(any(Team.class));
        verify(tournamentRepository, atLeastOnce()).save(any(Tournament.class));
        verify(teamRepository, atLeastOnce()).delete(any(Team.class));
    }
}