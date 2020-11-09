package com.handball.system.controller;

import com.handball.system.entity.*;
import com.handball.system.service.GameService;
import com.handball.system.service.TeamService;
import com.handball.system.service.TournamentService;
import com.handball.system.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String organizerPanel(){
        return "organizer/organizer";
    }

    @GetMapping("/tournaments")
    public String getOrganizerTournaments(Model model,@AuthenticationPrincipal User user){
        model.addAttribute("tournaments",tournamentService.findTournamentsByOrganizer(user));
        return "organizer/tournaments";
    }

    @GetMapping("/tournaments/{tournamentId}")
    public String getTournament(@PathVariable String tournamentId,Model model){
        model.addAttribute("tournament",tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        return "organizer/tournament";
    }

    @GetMapping("/tournaments/{tournamentId}/editTeams")
    public String showTeamsToEdit(@PathVariable String tournamentId,Model model){
        model.addAttribute("teams",teamService.findAllTeams());
        model.addAttribute("tournament",tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        return "organizer/showTeams";
    }

    @GetMapping("/tournaments/{tournamentId}/editTeams/{teamId}/add")
    public String addTeamToTournament(@PathVariable String tournamentId,@PathVariable String teamId){
        tournamentService.addTeamToTournament(tournamentService.findTournamentById(Long.valueOf(tournamentId)),teamService.findTeamById(Long.valueOf(teamId)));
        return "redirect:/organizer/tournaments/"+tournamentId+"/editTeams";
    }

    @GetMapping("/tournaments/{tournamentId}/editTeams/{teamId}/remove")
    public String removeTeamFromTournament(@PathVariable String tournamentId,@PathVariable String teamId){
        tournamentService.removeTeamFromTournament(tournamentService.findTournamentById(Long.valueOf(tournamentId)),teamService.findTeamById(Long.valueOf(teamId)));
        return "redirect:/organizer/tournaments/"+tournamentId+"/editTeams";
    }

    @GetMapping("/createTournament")
    public String createTournament(Tournament tournament, Model model){
        model.addAttribute("tournament",tournament);
        return "organizer/createTournament";
    }

    @PostMapping("/createTournament")
    public String createTournament(@Valid Tournament tournament, BindingResult errors,@AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "organizer/createTournament";
        }
        Tournament savedTournament = tournamentService.saveNewTournament(tournament,user);
        return "redirect:/organizer/tournaments/"+savedTournament.getId();
    }

    @GetMapping("/tournaments/{tournamentId}/createGame")
    public String createTournamentGame(@PathVariable String tournamentId,Model model,Game game){
        model.addAttribute("game",game);
        model.addAttribute("teams",tournamentService.findTournamentById(Long.valueOf(tournamentId)).getTeams());
        model.addAttribute("protocolists",userService.findAllUsersByRole(Role.PROTOCOLIST));
        return "organizer/createGame";
    }

    @PostMapping("/tournaments/{tournamentId}/createGame")
    public String createTournamentGame(@Valid Game game,BindingResult bindingResult, @PathVariable String tournamentId, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("game",game);
            model.addAttribute("teams",tournamentService.findTournamentById(Long.valueOf(tournamentId)).getTeams());
            model.addAttribute("protocolists",userService.findAllUsersByRole(Role.PROTOCOLIST));
            return "organizer/createGame";
        }
        game.setTournament(tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        game.setHomeTeamGoals(0);
        game.setAwayTeamGoals(0);
        gameService.saveGame(game);
        return "redirect:/organizer/tournaments/"+tournamentId;
    }

}
