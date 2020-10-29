package com.handball.system.controller;

import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.service.TournamentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/organizer")
public class OrganizerController {

    private final TournamentService tournamentService;

    public OrganizerController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("")
    public String organizerPanel(){
        return "organizer";
    }

    @GetMapping("/createTournament")
    public String createTournament(Tournament tournament){
        return "createTournament";
    }

    @PostMapping("/createTournament")
    String createTournament(@Valid Tournament tournament, BindingResult errors,@AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "createTournament";
        }
        tournamentService.saveNewTournament(tournament,user);
        return "redirect:/";
    }
}
