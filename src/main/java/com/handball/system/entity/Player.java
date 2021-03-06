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
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vārds nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    @NotNull
    private String name;
    @NotBlank(message = "Uzvārds nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    @NotNull
    private String surname;
    @NotBlank(message = "Pozīcija nevar būt tukša!")
    @Size(max = 80, message = "Maksimālais simbolu skaits ir 80!")
    @NotNull
    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @NotNull
    private Team team;

    public Player() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void validatePlayerForm(BindingResult bindingResult) {
        Player player = this;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Player>> violations = validator.validate(player);
        for (ConstraintViolation<Player> violation : violations) {
            if ((!violation.getPropertyPath().toString().equals("team"))) {
                bindingResult.rejectValue(violation.getPropertyPath().toString(), "errors." + violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
    }
}
