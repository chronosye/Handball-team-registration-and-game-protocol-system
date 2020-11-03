package com.handball.system.repository;

import com.handball.system.entity.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayerRepository extends JpaRepository<GamePlayer,Long> {
}
