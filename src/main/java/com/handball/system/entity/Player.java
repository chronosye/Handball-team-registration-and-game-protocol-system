package com.handball.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vārds nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    private String name;
    @NotBlank(message = "Uzvārds nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    private String surname;
    @NotBlank(message = "Pozīcija nevar būt tukša!")
    @Size(max = 80, message = "Maksimālais simbolu skaits ir 80!")
    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id")
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
}
