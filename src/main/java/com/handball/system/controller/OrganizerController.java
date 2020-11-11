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

    //showing all organizer created tournaments
    @GetMapping("/tournaments")
    public String getOrganizerTournaments(Model model,@AuthenticationPrincipal User user){
        model.addAttribute("tournaments",tournamentService.findTournamentsByOrganizer(user));
        return "organizer/tournaments";
    }

    //showing tournament info
    @GetMapping("/tournaments/{tournamentId}")
    public String getTournament(@PathVariable String tournamentId,Model model){
        model.addAttribute("tournament",tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        return "organizer/tournament";
    }

    //show list of teams to add for tournament
    @GetMapping("/tournaments/{tournamentId}/editTeams")
    public String showTeamsToEdit(@PathVariable String tournamentId,Model model){
        model.addAttribute("teams",teamService.findAllTeams());
        model.addAttribute("tournament",tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        model.addAttribute("gameTeams",gameService.findTeamsInGames(tournamentService.findTournamentById(Long.valueOf(tournamentId))));
        return "organizer/showTeams";
    }

    //add team to tournament
    @GetMapping("/tournaments/{tournamentId}/editTeams/{teamId}/add")
    public String addTeamToTournament(@PathVariable String tournamentId,@PathVariable String teamId){
        tournamentService.addTeamToTournament(tournamentService.findTournamentById(Long.valueOf(tournamentId)),teamService.findTeamById(Long.valueOf(teamId)));
        return "redirect:/organizer/tournaments/"+tournamentId+"/editTeams";
    }

    //remove team from tournament
    @GetMapping("/tournaments/{tournamentId}/editTeams/{teamId}/remove")
    public String removeTeamFromTournament(@PathVariable String tournamentId,@PathVariable String teamId){
        tournamentService.removeTeamFromTournament(tournamentService.findTournamentById(Long.valueOf(tournamentId)),teamService.findTeamById(Long.valueOf(teamId)));
        return "redirect:/organizer/tournaments/"+tournamentId+"/editTeams";
    }

    //creating new tournament
    @GetMapping("/createTournament")
    public String createTournament(Tournament tournament, Model model){
        model.addAttribute("tournament",tournament);
        return "organizer/createTournament";
    }

    //saving new tournament to database
    @PostMapping("/createTournament")
    public String createTournament(@Valid Tournament tournament, BindingResult errors,@AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "organizer/createTournament";
        }
        Tournament savedTournament = tournamentService.saveNewTournament(tournament,user);
        return "redirect:/organizer/tournaments/"+savedTournament.getId();
    }

    //creating new game for tournament
    @GetMapping("/tournaments/{tournamentId}/createGame")
    public String createTournamentGame(@PathVariable String tournamentId,Model model,Game game){
        model.addAttribute("game",game);
        model.addAttribute("teams",tournamentService.findTournamentById(Long.valueOf(tournamentId)).getTeams());
        model.addAttribute("protocolists",userService.findAllUsersByRole(Role.PROTOCOLIST));
        return "organizer/gameForm";
    }

    //saving new game into database
    @PostMapping("/tournaments/{tournamentId}/game")
    public String saveOrUpdateTournamentGame(@Valid Game game,BindingResult bindingResult, @PathVariable String tournamentId, Model model){
        if(game.getAwayTeam()==game.getHomeTeam() && game.getAwayTeam() != null){
            bindingResult.rejectValue("homeTeam","errors.homeTeam","Mājas un viesu komandas nevar būt vienādas");
            bindingResult.rejectValue("awayTeam","errors.awayTeam","Mājas un viesu komandas nevar būt vienādas");
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("game",game);
            model.addAttribute("teams",tournamentService.findTournamentById(Long.valueOf(tournamentId)).getTeams());
            model.addAttribute("protocolists",userService.findAllUsersByRole(Role.PROTOCOLIST));
            return "organizer/gameForm";
        }
        game.setTournament(tournamentService.findTournamentById(Long.valueOf(tournamentId)));
        game.setHomeTeamGoals(0);
        game.setAwayTeamGoals(0);
        gameService.saveGame(game);
        return "redirect:/organizer/tournaments/"+tournamentId;
    }

    //edit tournament game
    @GetMapping("/tournaments/{tournamentId}/game/{gameId}/edit")
    public String editGame(@PathVariable String tournamentId,@PathVariable String gameId,Model model){
        model.addAttribute("game",gameService.findGameById(Long.valueOf(gameId)));
        model.addAttribute("teams",tournamentService.findTournamentById(Long.valueOf(tournamentId)).getTeams());
        model.addAttribute("tournamentId",tournamentId);
        model.addAttribute("protocolists",userService.findAllUsersByRole(Role.PROTOCOLIST));
        return "organizer/gameForm";
    }

    //delete tournament game
    @GetMapping("/tournaments/{tournamentId}/game/{gameId}/delete")
    public String deleteGame(@PathVariable String tournamentId,@PathVariable String gameId){
        gameService.deleteGameById(Long.valueOf(gameId));
        return "redirect:/organizer/tournaments/"+tournamentId;
    }
}
