package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
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

class TournamentServiceTest {

    @InjectMocks
    TournamentService tournamentService;

    @Mock
    TournamentRepository tournamentRepository;

    private User user;
    private Tournament tournament;
    private List<Tournament> tournaments;
    private Set<Tournament> tournamentSet;
    private Team team;
    private Set<Team> teamsInGames;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        tournament = TestData.getTournament();
        tournaments = TestData.getTournamentsList();
        tournamentSet = TestData.getTournaments();
        team = TestData.getTeam();
        teamsInGames = TestData.getTeamsSet();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveOrUpdateTournament() {
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.of(tournament));
        tournamentService.saveOrUpdateTournament(tournament, user);
        verify(tournamentRepository, atLeastOnce()).findById(anyLong());
        verify(tournamentRepository, atLeastOnce()).save(any(Tournament.class));
        tournament.setId(null);
        tournamentService.saveOrUpdateTournament(tournament, user);
        verify(tournamentRepository, atLeastOnce()).findById(anyLong());
        verify(tournamentRepository, atLeastOnce()).save(any(Tournament.class));
    }

    @Test
    void findAllTournaments() {
        when(tournamentRepository.findAll()).thenReturn(tournaments);
        tournamentService.findAllTournaments();
        verify(tournamentRepository, atLeastOnce()).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void addTeamToTournament() {
        tournamentService.addTeamToTournament(tournament, team);
        verify(tournamentRepository, atLeastOnce()).save(any(Tournament.class));
    }

    @Test
    void removeTeamFromTournament() {
        tournamentService.removeTeamFromTournament(tournament, team, teamsInGames);
        verify(tournamentRepository, atLeastOnce()).save(any(Tournament.class));
    }

    @Test
    void findTournamentsByOrganizer() {
        when(tournamentRepository.findByOrganizer(any(User.class))).thenReturn(tournamentSet);
        tournamentService.findTournamentsByOrganizer(user);
        verify(tournamentRepository, atLeastOnce()).findByOrganizer(any(User.class));
    }

    @Test
    void findTournamentById() {
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.of(tournament));
        tournamentService.findTournamentById(tournament.getId());
        verify(tournamentRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void findTournamentByIdAndOrganizer() {
        when(tournamentRepository.findByIdAndOrganizer(anyLong(), any(User.class))).thenReturn(tournament);
        tournamentService.findTournamentByIdAndOrganizer(tournament.getId(), user);
        verify(tournamentRepository, atLeastOnce()).findByIdAndOrganizer(anyLong(), any(User.class));
    }

    @Test
    void deleteTournament() {
        tournamentService.deleteTournament(tournament);
        verify(tournamentRepository, atLeastOnce()).delete(any(Tournament.class));
    }
}