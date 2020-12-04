package com.handball.system.repository;

import com.handball.system.entity.Game;
import com.handball.system.entity.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocolRepository extends JpaRepository<Protocol, Long> {

    Protocol findProtocolByGame(Game game);
}
