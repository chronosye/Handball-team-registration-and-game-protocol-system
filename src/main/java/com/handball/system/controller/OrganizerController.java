package com.handball.system.controller;

import com.handball.system.entity.*;
import com.handball.system.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;


@Transactional
@Controller
@RequestMapping("/organizer")
public class OrganizerController {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final GameService gameService;
    private final UserService userService;

    public OrganizerController(TournamentService tournamentService, TeamService teamService, GameService gameService, UserService userService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("")
    public String organizerPanel() {
        return "organizer/organizer";
    }

    //showing all organizer created tournaments
    @GetMapping("/tournaments")
    public String getOrganizerTournaments(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("tournaments", tournamentService.findTournamentsByOrganizer(user));
        return "organizer/tournaments";
    }

    //showing tournament info
    @GetMapping("/tournaments/{tournamentId}")
    public String getTournament(@PathVariable String tournamentId, Model model, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("tournament", tournament);
        return "organizer/tournament";
    }

    //show list of teams to add for tournament
    @GetMapping("/tournaments/{tournamentId}/editTeams")
    public String showTeamsToEdit(@PathVariable String tournamentId, Model model, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("teams", teamService.findAllTeams());
        model.addAttribute("tournament", tournament);
        model.addAttribute("gameTeams", gameService.findTeamsInGames(tournament));
        return "organizer/showTeams";
    }

    //add team to tournament
    @GetMapping("/tournaments/{tournamentId}/editTeams/{teamId}/add")
    public String addTeamToTournament(@PathVariable String tournamentId, @PathVariable String teamId, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        Team team = teamService.findTeamById(Long.valueOf(teamId));
        if (tournament == null || team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        tournamentService.addTeamToTournament(tournament, team);
        return "redirect:/organizer/tournaments/" + tournamentId + "/editTeams";
    }

    //remove team from tournament
    @GetMapping("/tournaments/{tournamentId}/editTeams/{teamId}/remove")
    public String removeTeamFromTournament(@PathVariable String tournamentId, @PathVariable String teamId, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        Team team = teamService.findTeamById(Long.valueOf(teamId));
        if (tournament == null || team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        tournamentService.removeTeamFromTournament(tournament, team);
        return "redirect:/organizer/tournaments/" + tournamentId + "/editTeams";
    }

    //creating or editing tournament
    @GetMapping("/createTournament")
    public String createTournament(Tournament tournament, Model model) {
        model.addAttribute("tournament", tournament);
        return "organizer/tournamentForm";
    }

    @GetMapping("/tournaments/{tournamentId}/edit")
    public String editTournament(Model model, @PathVariable String tournamentId, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("tournament", tournament);
        return "organizer/tournamentForm";
    }

    //saving new tournament to database
    @PostMapping("/createOrUpdateTournament")
    public String createTournament(@Valid Tournament tournament, BindingResult errors, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "organizer/tournamentForm";
        }
        Tournament savedTournament = tournamentService.saveOrUpdateTournament(tournament, user);
        return "redirect:/organizer/tournaments/" + savedTournament.getId();
    }

    //creating new game for tournament
    @GetMapping("/tournaments/{tournamentId}/createGame")
    public String createTournamentGame(@PathVariable String tournamentId, Model model, Game game, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("game", game);
        model.addAttribute("teams", tournament.getTeams());
        model.addAttribute("protocolists", userService.findAllUsersByRole(Role.PROTOCOLIST));
        return "organizer/gameForm";
    }

    //saving new game into database
    @PostMapping("/tournaments/{tournamentId}/game")
    public String saveOrUpdateTournamentGame(@Valid Game game, BindingResult bindingResult, @PathVariable String tournamentId, Model model, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (game.getAwayTeam() == game.getHomeTeam() && game.getAwayTeam() != null) {
            bindingResult.rejectValue("homeTeam", "errors.homeTeam", "Mājas un viesu komandas nevar būt vienādas");
            bindingResult.rejectValue("awayTeam", "errors.awayTeam", "Mājas un viesu komandas nevar būt vienādas");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("game", game);
            model.addAttribute("teams", tournament.getTeams());
            model.addAttribute("protocolists", userService.findAllUsersByRole(Role.PROTOCOLIST));
            return "organizer/gameForm";
        }
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameService.saveGame(game, tournament);
        return "redirect:/organizer/tournaments/" + tournamentId;
    }

    //edit tournament game
    @GetMapping("/tournaments/{tournamentId}/game/{gameId}/edit")
    public String editGame(@PathVariable String tournamentId, @PathVariable String gameId, Model model, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        Game game = gameService.findGameByIdAndTournament(Long.valueOf(gameId), tournament);
        if (tournament == null || game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("game", game);
        model.addAttribute("teams", tournament.getTeams());
        model.addAttribute("tournamentId", tournamentId);
        model.addAttribute("protocolists", userService.findAllUsersByRole(Role.PROTOCOLIST));
        return "organizer/gameForm";
    }

    //delete tournament game
    @GetMapping("/tournaments/{tournamentId}/game/{gameId}/delete")
    public String deleteGame(@PathVariable String tournamentId, @PathVariable String gameId, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameService.deleteGameByIdAndTournament(Long.valueOf(gameId), tournament);
        return "redirect:/organizer/tournaments/" + tournamentId;
    }

    @GetMapping("/tournaments/{tournamentId}/delete")
    public String deleteTournament(@PathVariable String tournamentId, @AuthenticationPrincipal User user) {
        Tournament tournament = tournamentService.findTournamentByIdAndOrganizer(Long.valueOf(tournamentId), user);
        if (tournament == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        tournamentService.deleteTournament(tournament);
        return "redirect:/organizer/tournaments";
    }
}
