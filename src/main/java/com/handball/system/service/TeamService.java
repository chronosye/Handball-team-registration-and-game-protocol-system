package com.handball.system.service;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.repository.PlayerRepository;
import com.handball.system.repository.TeamRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public boolean hasManagerTeam(User user){
        if(teamRepository.findByManager(user)==null){
            return false;
        }
        else{
            return true;
        }
    }

    public Team findTeamById(Long id){
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Team saveTeam(Team team,User manager){
        team.setManager(manager);
        return teamRepository.save(team);
    }

    public void deleteTeamById(Long id){
        Team team = findTeamById(id);
        Set<Player> teamPlayers = playerRepository.findPlayersByTeam(team);
        for(Player player : teamPlayers){
            playerRepository.delete(player);
        }
        teamRepository.deleteById(id);
    }
}
