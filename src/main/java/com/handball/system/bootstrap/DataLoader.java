package com.handball.system.bootstrap;

import com.handball.system.entity.Player;
import com.handball.system.entity.Team;
import com.handball.system.repository.PlayerRepository;
import com.handball.system.repository.TeamRepository;
import com.handball.system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public DataLoader(PlayerRepository playerRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;

        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        Team team = new Team();
        team.setName("Tenax");
        team.setManager(userRepository.getOne(1L));

        Player player1 = new Player();
        player1.setName("test");
        player1.setSurname("aaa");
        player1.setPosition("GK");
        player1.setTeam(team);
        team.getPlayers().add(player1);

        teamRepository.save(team);
    }
}
