package com.handball.system.service;

import com.handball.system.entity.*;
import com.handball.system.repository.GameRepository;
import com.handball.system.repository.TeamRepository;
import com.handball.system.repository.TournamentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final TournamentRepository tournamentRepository;

    public TeamService(TeamRepository teamRepository, GameRepository gameRepository, TournamentRepository tournamentRepository) {
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public boolean hasManagerTeam(User user) {
        return teamRepository.findByManager(user) != null;
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Team findTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team findTeamByManager(User user) {
        return teamRepository.findByManager(user);
    }

    public void updateTeam(Team team) {
        Team teamToEdit = teamRepository.findById(team.getId()).get();
        teamToEdit.setName(team.getName());
        teamRepository.save(teamToEdit);
    }

    public void saveTeam(Team team, User manager) {
        team.setManager(manager);
        for (Player player : team.getPlayers()) {
            player.setTeam(team);
        }
        if (teamRepository.findByManager(manager) == null) teamRepository.save(team);
    }

    public void deleteTeam(Team team) {
        Set<Game> teamGames = gameRepository.findAllByHomeTeamOrAwayTeam(team, team);
        if (teamGames != null) {
            for (Game game : teamGames) {
                gameRepository.delete(game);
            }
        }
        Set<Tournament> teamTournaments = tournamentRepository.findAllByTeamsContaining(team);
        if (teamTournaments != null) {
            for (Tournament tournament : teamTournaments) {
                tournament.getTeams().remove(team);
                tournamentRepository.save(tournament);
            }
        }
        teamRepository.delete(team);
    }
}
