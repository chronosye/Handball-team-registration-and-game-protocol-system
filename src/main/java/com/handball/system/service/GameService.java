package com.handball.system.service;

import com.handball.system.entity.Game;
import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
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

    public Game saveGame(Game game){
        return gameRepository.save(game);
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

    public void deleteGameById(Long id){
        gameRepository.deleteById(id);
    }
}
