package com.handball.system.service;

import com.handball.system.entity.Player;
import com.handball.system.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findPlayerById(Long id){
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Player savePlayer(Player player){
        return playerRepository.save(player);
    }

    public void deletePlayerById(Long id){
        playerRepository.deleteById(id);
    }
}
