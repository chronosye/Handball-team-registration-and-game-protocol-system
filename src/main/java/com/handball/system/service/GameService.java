package com.handball.system.service;

import com.handball.system.entity.*;
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

    public void saveGame(Game game, Tournament tournament){
        game.setTournament(tournament);
        game.setHomeTeamGoals(0);
        game.setAwayTeamGoals(0);
        game.setEnded(false);
        gameRepository.save(game);
    }

    public void endGame(Game game, Set<PlayerStats> homeGamePlayers, Set<PlayerStats> awayGamePlayers){
        int homeGoals = 0;
        int awayGoals = 0;
        for(PlayerStats gamePlayer:homeGamePlayers){
            homeGoals = homeGoals + gamePlayer.getGoals();
        }
        for(PlayerStats gamePlayer:awayGamePlayers){
            awayGoals = awayGoals + gamePlayer.getGoals();
        }
        game.setHomeTeamGoals(homeGoals);
        game.setAwayTeamGoals(awayGoals);
        game.setEnded(true);
        gameRepository.save(game);
    }

    public Game findGameById(Long id){
        return gameRepository.findById(id).get();
    }

    public Set<Team> findTeamsInGames(Tournament tournament){
        Set<Game> games = gameRepository.findAllByTournament(tournament);
        Set<Team> teamsInGames = new HashSet<>();
        for(Game game : games){
            teamsInGames.add(game.getAwayTeam());
            teamsInGames.add(game.getHomeTeam());
        }
        return teamsInGames;
    }

    public Set<Game> findGamesByProtocolist(User protocolist){
        return gameRepository.findAllByProtocolist(protocolist);
    }

    public void deleteGameById(Long id){
        gameRepository.deleteById(id);
    }
}
