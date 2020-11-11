package com.handball.system.repository;

import com.handball.system.entity.Game;
import com.handball.system.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
    Set<Game> findAllByTournament(Tournament tournament);
}
