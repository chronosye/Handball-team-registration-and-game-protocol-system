package com.handball.system.service;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findPlayerByIdAndTeam(Long id, Team team) {
        return playerRepository.findByIdAndTeam(id, team).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Player findPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public List<Player> findPlayersByTeam(Team team) {
        return playerRepository.findPlayersByTeam(team);
    }

    public void deletePlayerByIdAndTeam(Long id, Team team) {
        List<Player> players = playerRepository.findPlayersByTeam(team);
        if(players.size()>6){
            Player player = playerRepository.findByIdAndTeam(id, team).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            playerRepository.delete(player);
        }else{
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
