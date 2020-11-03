package com.handball.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToMany(mappedBy = "teams")
    private Set<Tournament> tournaments= new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    @Size(min = 6,message = "Minimālais spēlētāju skaits ir 6!")
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
}
