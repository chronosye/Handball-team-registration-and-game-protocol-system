package com.handball.system.controller;

import com.handball.system.entity.Game;
import com.handball.system.service.GameService;
import com.handball.system.service.ProtocolService;
import com.handball.system.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final GameService gameService;
    private final ProtocolService protocolService;

    public TournamentController(TournamentService tournamentService, GameService gameService, ProtocolService protocolService) {
        this.tournamentService = tournamentService;
        this.gameService = gameService;
        this.protocolService = protocolService;
    }

    @GetMapping("")
    public String tournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.findAllTournaments());
        return "tournaments";
    }

    @GetMapping("/{tournamentId}")
    public String tournament(Model model, @PathVariable String tournamentId) {
        model.addAttribute("tournament", tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        return "tournament";
    }

    @GetMapping("/{tournamentId}/game/{gameId}")
    public String tournamentGameInfo(Model model, @PathVariable String tournamentId, @PathVariable String gameId){
        Game game = gameService.findGameById(Long.valueOf(gameId));
        model.addAttribute("game",game);
        model.addAttribute("protocol",protocolService.getProtocolByGame(game));
        return "game";
    }
}
