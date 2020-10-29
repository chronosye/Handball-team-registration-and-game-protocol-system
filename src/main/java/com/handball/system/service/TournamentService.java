package com.handball.system.service;

import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Tournament saveNewTournament(Tournament tournament, User organizer){
        tournament.setOrganizer(organizer);
        return tournamentRepository.save(tournament);
    }

    public Set<Tournament> findAllTournaments(){
        Set<Tournament> tournaments = new HashSet<>();
        tournamentRepository.findAll().forEach(tournaments::add);
        return tournaments;
    }
}
