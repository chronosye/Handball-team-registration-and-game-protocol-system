package com.handball.system.controller;

import com.handball.system.entity.Game;
import com.handball.system.entity.Protocol;
import com.handball.system.entity.Team;
import com.handball.system.entity.User;
import com.handball.system.service.GameService;
import com.handball.system.service.PlayerService;
import com.handball.system.service.PlayerStatsService;
import com.handball.system.service.ProtocolService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/protocolist")
public class ProtocolistController {

    private final GameService gameService;
    private final PlayerService playerService;
    private final PlayerStatsService playerStatsService;
    private final ProtocolService protocolService;

    public ProtocolistController(GameService gameService, PlayerService playerService, PlayerStatsService playerStatsService, ProtocolService protocolService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.playerStatsService = playerStatsService;
        this.protocolService = protocolService;
    }

    @GetMapping("")
    public String protocolistHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("games", gameService.findGamesByProtocolist(user));
        return "protocolist/protocolist";
    }

    @GetMapping("/game/{gameId}/addProtocol")
    public String addProtocol(@PathVariable String gameId, Model model, @AuthenticationPrincipal User user) {
        Game game = gameService.findGameByIdAndProtocolist(Long.valueOf(gameId), user);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        Protocol protocol = new Protocol(playerService.findPlayersByTeam(homeTeam), playerService.findPlayersByTeam(awayTeam));
        model.addAttribute("game", game);
        model.addAttribute("homeTeam", homeTeam);
        model.addAttribute("awayTeam", awayTeam);
        model.addAttribute("protocol", protocol);
        return "protocolist/protocolForm";
    }

    @GetMapping("/game/{gameId}/editProtocol")
    public String editProtocol(@PathVariable String gameId, Model model, @AuthenticationPrincipal User user) {
        Game game = gameService.findGameByIdAndProtocolist(Long.valueOf(gameId), user);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        Protocol protocol = new Protocol(playerService.findPlayersByTeam(homeTeam), playerService.findPlayersByTeam(awayTeam));
        model.addAttribute("game", game);
        model.addAttribute("homeTeam", homeTeam);
        model.addAttribute("awayTeam", awayTeam);
        model.addAttribute("protocol", protocolService.getProtocolToEdit(game, protocol));
        return "protocolist/protocolForm";
    }

    @PostMapping("/game/{gameId}/addProtocol")
    public String Protocol(@PathVariable String gameId, @ModelAttribute Protocol protocol, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        protocol.validateProtocolForm(bindingResult);
        playerStatsService.validatePlayerStats(protocol.getHomeTeamPlayerStats(), bindingResult, true);
        playerStatsService.validatePlayerStats(protocol.getAwayTeamPlayerStats(), bindingResult, false);
        Game game = gameService.findGameByIdAndProtocolist(Long.valueOf(gameId), user);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors()) {
            Team homeTeam = game.getHomeTeam();
            Team awayTeam = game.getAwayTeam();
            model.addAttribute("game", game);
            model.addAttribute("homeTeam", homeTeam);
            model.addAttribute("awayTeam", awayTeam);
            return "protocolist/protocolForm";
        }
        protocolService.saveProtocol(protocol, game);
        return "redirect:/protocolist";
    }
}