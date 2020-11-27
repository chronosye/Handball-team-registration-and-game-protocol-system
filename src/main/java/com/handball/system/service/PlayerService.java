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
        return playerRepository.findById(id).get();
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> findPlayersByTeam(Team team) {
        return playerRepository.findPlayersByTeam(team);
    }

    public void deletePlayerByIdAndTeam(Long id, Team team) {
        Player player = playerRepository.findByIdAndTeam(id, team).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        playerRepository.delete(player);
    }
}
