package com.handball.system.entity;

import org.springframework.validation.BindingResult;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Komandas nosaukums nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    @NotNull
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    @NotNull
    private User manager;

    @ManyToMany(mappedBy = "teams")
    private Set<Tournament> tournaments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    @Size(min = 6, message = "Minimālais spēlētāju skaits ir 6!")
    private List<Player> players = new ArrayList<>();

    public Team() {
    }

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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void validateTeamForm(BindingResult bindingResult) {
        Team team = this;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Team>> violations = validator.validate(team);
        for (ConstraintViolation<Team> violation : violations) {
            if ((!violation.getPropertyPath().toString().equals("manager"))) {
                bindingResult.rejectValue(violation.getPropertyPath().toString(), "errors." + violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        int counter = 0;
        for (Player player : players) {
            Set<ConstraintViolation<Player>> playerViolations = validator.validate(player);
            for (ConstraintViolation<Player> violation : playerViolations) {
                if ((!violation.getPropertyPath().toString().equals("team"))) {
                    bindingResult.rejectValue("players[" + counter + "]." + violation.getPropertyPath().toString(), "errors." + "players[" + counter + "]." + violation.getPropertyPath().toString(), violation.getMessage());
                }
            }
            counter++;
        }
    }
}
