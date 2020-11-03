package com.handball.system.repository;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    Set<Player> findPlayersByTeam(Team team);
}
