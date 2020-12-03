package com.handball.system.service;

import com.handball.system.entity.Team;
import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.repository.TournamentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Tournament saveNewTournament(Tournament tournament, User organizer) {
        tournament.setOrganizer(organizer);
        return tournamentRepository.save(tournament);
    }

    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void addTeamToTournament(Tournament tournament, Team team) {
        tournament.getTeams().add(team);
        tournamentRepository.save(tournament);
    }

    public void removeTeamFromTournament(Tournament tournament, Team team) {
        tournament.getTeams().remove(team);
        tournamentRepository.save(tournament);
    }

    public Set<Tournament> findTournamentsByOrganizer(User user) {
        return tournamentRepository.findByOrganizer(user);
    }

    public Tournament findTournamentById(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Tournament findTournamentByIdAndOrganizer(Long id, User user) {
        return tournamentRepository.findByIdAndOrganizer(id, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteTournament(Tournament tournament){
        tournamentRepository.delete(tournament);
    }
}
