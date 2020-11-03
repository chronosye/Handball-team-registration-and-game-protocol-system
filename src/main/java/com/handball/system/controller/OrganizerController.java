package com.handball.system.controller;

import com.handball.system.entity.Tournament;
import com.handball.system.entity.User;
import com.handball.system.service.TeamService;
import com.handball.system.service.TournamentService;
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

    public OrganizerController(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
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

    @GetMapping("/tournaments/{id}/editTeams")
    public String showTeamsToEdit(@PathVariable String id,Model model){
        model.addAttribute("teams",teamService.findAllTeams());
        model.addAttribute("tournament",tournamentService.findTournamentById(Long.valueOf(id)));
        return "organizer/showTeams";
    }

    @GetMapping("/tournaments/{id}/editTeams/{teamId}/add")
    public String addTeamToTournament(@PathVariable String id,@PathVariable String teamId){
        tournamentService.addTeamToTournament(tournamentService.findTournamentById(Long.valueOf(id)),teamService.findTeamById(Long.valueOf(teamId)));
        return "redirect:/organizer/tournaments/"+id+"/editTeams";
    }

    @GetMapping("/tournaments/{id}/editTeams/{teamId}/remove")
    public String removeTeamFromTournament(@PathVariable String id,@PathVariable String teamId){
        tournamentService.removeTeamFromTournament(tournamentService.findTournamentById(Long.valueOf(id)),teamService.findTeamById(Long.valueOf(teamId)));
        return "redirect:/organizer/tournaments/"+id+"/editTeams";
    }

    @GetMapping("/createTournament")
    public String createTournament(Tournament tournament, Model model){
        model.addAttribute("tournament",tournament);
        return "organizer/createTournament";
    }

    @PostMapping("/createTournament")
    String createTournament(@Valid Tournament tournament, BindingResult errors,@AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "organizer/createTournament";
        }
        Tournament savedTournament = tournamentService.saveNewTournament(tournament,user);
        return "redirect:/tournaments/"+savedTournament.getId();
    }
}
