package com.handball.system.service;

import com.handball.system.entity.Game;
import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void saveGame(Game game, Tournament tournament) {
        game.setTournament(tournament);
        game.setHomeTeamGoals(0);
        game.setAwayTeamGoals(0);
        game.setEnded(false);
        gameRepository.save(game);
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public Game findGameByIdAndTournament(Long id, Tournament tournament) {
        return gameRepository.findByIdAndTournament(id, tournament);
    }

    public Game findGameByIdAndProtocolist(Long id, User user) {
        return gameRepository.findByIdAndProtocolist(id, user);
    }

    public Set<Team> findTeamsInGames(Tournament tournament) {
        Set<Game> games = gameRepository.findAllByTournament(tournament);
        Set<Team> teamsInGames = new HashSet<>();
        for (Game game : games) {
            teamsInGames.add(game.getAwayTeam());
            teamsInGames.add(game.getHomeTeam());
        }
        return teamsInGames;
    }

    public Set<Game> findGamesByProtocolist(User protocolist) {
        return gameRepository.findAllByProtocolist(protocolist);
    }

    public void deleteGameByIdAndTournament(Long id, Tournament tournament) {
        gameRepository.deleteGameByIdAndTournament(id, tournament);
    }
}
