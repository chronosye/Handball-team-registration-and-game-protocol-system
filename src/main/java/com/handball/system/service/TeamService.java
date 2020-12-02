package com.handball.system.service;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.repository.TeamRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public boolean hasManagerTeam(User user) {
        if (teamRepository.findByManager(user) == null) {
            return false;
        } else {
            return true;
        }
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Team findTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Team findTeamByManager(User user) {
        return teamRepository.findByManager(user);
    }

    public void saveTeam(Team team, User manager) {
        team.setManager(manager);
        for (Player player : team.getPlayers()) {
            player.setTeam(team);
        }
        teamRepository.save(team);
    }
}
