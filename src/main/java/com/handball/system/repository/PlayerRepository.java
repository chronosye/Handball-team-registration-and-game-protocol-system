package com.handball.system.repository;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findPlayersByTeam(Team team);

    Optional<Player> findByIdAndTeam(Long id, Team team);
}
