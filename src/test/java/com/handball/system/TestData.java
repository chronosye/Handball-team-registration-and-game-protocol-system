package com.handball.system;

import com.handball.system.entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestData {

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Jack");
        user.setSurname("Sparrow");
        user.setEmail("test@test");
        user.setPassword("TestPassword");
        return user;
    }

    public static User getUser2() {
        User user = new User();
        user.setId(2L);
        user.setName("Emma");
        user.setSurname("Nothing");
        user.setEmail("ab@cd");
        user.setPassword("12345678");
        return user;
    }

    public static Tournament getTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setName("test tournament");
        tournament.setOrganizer(getUser());
        return tournament;
    }

    public static Game getGame() {
        Game game = new Game();
        game.setId(1L);
        game.setTournament(getTournament());
        return game;
    }

    public static Protocol getProtocol() {
        Protocol protocol = new Protocol();
        protocol.setId(1L);
        protocol.setGame(getGame());
        return protocol;
    }

    public static Team getTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setName("testTeam");
        team.setPlayers(getTeamPlayers());
        return team;
    }

    public static Team getTeam2() {
        Team team = new Team();
        team.setId(2L);
        team.setName("testTeam");
        return team;
    }

    public static List<Team> getTeams() {
        List<Team> teamList = new ArrayList<>();
        teamList.add(getTeam());
        teamList.add(getTeam2());
        return teamList;
    }

    public static Set<Team> getTeamsSet() {
        Set<Team> teamSet = new HashSet<>();
        teamSet.add(getTeam());
        teamSet.add(getTeam2());
        return teamSet;
    }

    public static Player getPlayer() {
        Player player = new Player();
        player.setName("Joe");
        player.setSurname("Doe");
        player.setPosition("GK");
        return player;
    }

    public static List<Player> getTeamPlayers() {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Player player = new Player();
            player.setName("Joe" + i);
            player.setSurname("Doe" + i);
            player.setPosition("GK" + i);
            player.setId((long) i);
            playerList.add(player);
        }
        return playerList;
    }

    public static Set<Game> getGames() {
        Set<Game> games = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Game game = new Game();
            game.setId((long) i);
            game.setTournament(getTournament());
            games.add(game);
        }
        return games;
    }

    public static Set<Tournament> getTournaments() {
        Set<Tournament> tournaments = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Tournament tournament = new Tournament();
            tournament.setId((long) i);
            tournament.setName("tournamentNR." + i);
            tournament.setOrganizer(getUser());
            tournaments.add(tournament);
        }
        return tournaments;
    }

    public static List<Tournament> getTournamentsList() {
        List<Tournament> tournaments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Tournament tournament = new Tournament();
            tournament.setId((long) i);
            tournament.setName("tournamentNR." + i);
            tournament.setOrganizer(getUser());
            tournaments.add(tournament);
        }
        return tournaments;
    }

    public static List<PlayerStats> getPlayerStats() {
        List<PlayerStats> playerStats = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            PlayerStats playerStat = new PlayerStats();
            playerStat.setId((long) i);
            playerStat.setShots(i + 1);
            playerStat.setGoals(i);
            playerStat.setPlayer(getPlayer());
            playerStat.getPlayer().setId((long) i);
            playerStat.setPlayingGame(true);
            playerStats.add(playerStat);
        }
        return playerStats;
    }
}
