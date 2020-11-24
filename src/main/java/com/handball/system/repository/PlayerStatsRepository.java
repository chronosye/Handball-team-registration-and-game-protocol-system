package com.handball.system.repository;

import com.handball.system.entity.PlayerStats;
import com.handball.system.entity.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
    void deleteGamePlayersByProtocol(Protocol protocol);
}
