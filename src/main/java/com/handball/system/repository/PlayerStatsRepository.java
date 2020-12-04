package com.handball.system.repository;

import com.handball.system.entity.Player;
import com.handball.system.entity.PlayerStats;
import com.handball.system.entity.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {

    List<PlayerStats> findAllByPlayer(Player player);

    void deleteGamePlayersByProtocol(Protocol protocol);
}
