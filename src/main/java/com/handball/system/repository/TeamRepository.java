package com.handball.system.repository;

import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByManager(User user);

    void deleteAllByManager(User user);
}
