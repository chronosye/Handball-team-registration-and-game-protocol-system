package com.handball.system.repository;

import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long> {
    Set<Tournament> findByOrganizer(User user);
}
