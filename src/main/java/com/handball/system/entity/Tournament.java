package com.handball.system.entity;

import org.springframework.validation.BindingResult;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nosaukums nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    @JoinTable(
            name = "tournaments_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @NotNull
    private User organizer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    @OrderBy("date ASC")
    private Set<Game> games = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void validateTournamentForm(BindingResult bindingResult) {
        Tournament tournament = this;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tournament>> violations = validator.validate(tournament);
        for (ConstraintViolation<Tournament> violation : violations) {
            if ((!violation.getPropertyPath().toString().equals("organizer"))) {
                bindingResult.rejectValue(violation.getPropertyPath().toString(), "errors." + violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
    }
}
