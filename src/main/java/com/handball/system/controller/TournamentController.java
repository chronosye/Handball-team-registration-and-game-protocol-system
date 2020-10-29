package com.handball.system.controller;

import com.handball.system.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("")
    public String tournaments(Model model){
        model.addAttribute("tournaments",tournamentService.findAllTournaments());
        return "tournaments";
    }
}
