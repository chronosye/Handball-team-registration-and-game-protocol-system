package com.handball.system.repository;

import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Set<Tournament> findByOrganizer(User user);

    Set<Tournament> findAllByTeamsContaining(Team team);

    Optional<Tournament> findByIdAndOrganizer(Long id, User user);

    void deleteAllByOrganizer(User user);
}
