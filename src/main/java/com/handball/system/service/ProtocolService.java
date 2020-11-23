package com.handball.system.service;

import com.handball.system.entity.Game;
import com.handball.system.entity.PlayerStats;
import com.handball.system.entity.Protocol;
import com.handball.system.repository.PlayerStatsRepository;
import com.handball.system.repository.ProtocolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final PlayerStatsRepository playerStatsRepository;

    public ProtocolService(ProtocolRepository protocolRepository, PlayerStatsRepository playerStatsRepository) {
        this.protocolRepository = protocolRepository;
        this.playerStatsRepository = playerStatsRepository;
    }

    public Protocol getProtocolToEdit(Game game, Protocol newProtocol){
        Protocol protocolFind = protocolRepository.findProtocolByGame(game);
        for(PlayerStats playerStats : protocolFind.getHomeTeamPlayerStats()){
            for(PlayerStats blankPlayerStats : newProtocol.getHomeTeamPlayerStats()){
                if(playerStats.getPlayer().getId().equals(blankPlayerStats.getPlayer().getId())){
                    blankPlayerStats.setGoals(playerStats.getGoals());
                    blankPlayerStats.setShots(playerStats.getShots());
                    blankPlayerStats.setPlayingGame(true);
                    blankPlayerStats.setId(playerStats.getId());
                }
            }
        }
        for(PlayerStats playerStats : protocolFind.getAwayTeamPlayerStats()){
            for(PlayerStats blankPlayerStats : newProtocol.getAwayTeamPlayerStats()){
                if(playerStats.getPlayer().getId().equals(blankPlayerStats.getPlayer().getId())){
                    blankPlayerStats.setGoals(playerStats.getGoals());
                    blankPlayerStats.setShots(playerStats.getShots());
                    blankPlayerStats.setPlayingGame(true);
                    blankPlayerStats.setId(playerStats.getId());
                }
            }
        }
        newProtocol.setId(protocolFind.getId());
        return newProtocol;
    }

    public void saveProtocol(Protocol protocol,Game game){
        if(protocol.getId()!=null){
            playerStatsRepository.deleteGamePlayersByProtocol(protocol);
        }else{
            game.setProtocol(protocol);
        }
        int homeTeamGoals = 0;
        int awayTeamGoals = 0;
        List<PlayerStats> homeTeamPlayerStats = protocol.getHomeTeamPlayerStats();
        List<PlayerStats> awayTeamPlayerStats = protocol.getAwayTeamPlayerStats();
        homeTeamPlayerStats.removeIf(playerStats -> !playerStats.getPlayingGame());
        awayTeamPlayerStats.removeIf(playerStats -> !playerStats.getPlayingGame());
        for(PlayerStats playerStats : homeTeamPlayerStats){
            playerStats.setProtocol(protocol);
            homeTeamGoals = homeTeamGoals + playerStats.getGoals();
        }
        for(PlayerStats playerStats : awayTeamPlayerStats){
            playerStats.setProtocol(protocol);
            awayTeamGoals = awayTeamGoals + playerStats.getGoals();
        }
        game.setHomeTeamGoals(homeTeamGoals);
        game.setAwayTeamGoals(awayTeamGoals);
        game.setEnded(true);
        protocol.setHomeTeamPlayerStats(homeTeamPlayerStats);
        protocol.setAwayTeamPlayerStats(awayTeamPlayerStats);
        protocolRepository.save(protocol);
    }
}
