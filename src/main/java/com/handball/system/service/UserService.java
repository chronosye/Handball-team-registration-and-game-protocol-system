package com.handball.system.service;

import com.handball.system.entity.*;
import com.handball.system.repository.GameRepository;
import com.handball.system.repository.TeamRepository;
import com.handball.system.repository.TournamentRepository;
import com.handball.system.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TournamentRepository tournamentRepository, TeamRepository teamRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Set<User> findAllUsersByRole(Role role) {
        return new HashSet<>(userRepository.findUsersByRolesContaining(role));
    }

    public void addRole(User user, Role role) {
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void removeRole(User user, Role role) {
        if (role == Role.ORGANIZER) {
            tournamentRepository.deleteAllByOrganizer(user);
        } else if (role == Role.MANAGER) {
            Team team = teamRepository.findByManager(user);
            if (team != null) {
                Set<Game> managerTeamGames = gameRepository.findAllByHomeTeamOrAwayTeam(team, team);
                if (managerTeamGames != null) {
                    for (Game game : managerTeamGames) {
                        gameRepository.delete(game);
                    }
                }
                Set<Tournament> managerTeamTournaments = tournamentRepository.findAllByTeamsContaining(team);
                if (managerTeamTournaments != null) {
                    for (Tournament tournament : managerTeamTournaments) {
                        tournament.getTeams().remove(team);
                        tournamentRepository.save(tournament);
                    }
                }
                teamRepository.deleteAllByManager(user);
            }
        } else if (role == Role.PROTOCOLIST) {
            gameRepository.deleteAllByProtocolist(user);
        }
        Set<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUserData(User formUser, User user) {
        user.setName(formUser.getName());
        user.setSurname(formUser.getSurname());
        user.setEmail(formUser.getEmail());
        userRepository.save(user);
    }

    public boolean validatePasswordChange(User user, String oldPassword, String newPassword, String newPasswordRepeat, Model model) {
        boolean flag = false;
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("oldPasswordError", "Ievadītā vecā parole nav pareiza!");
            flag = true;
        } else if (newPassword.length() < 8) {
            model.addAttribute("newPasswordError", "Minimālais paroles garums ir 8 simboli!");
            flag = true;
        } else if (newPassword.length() > 80) {
            model.addAttribute("newPasswordError", "Maksimālais paroles garums ir 80 simboli!");
            flag = true;
        } else if (!newPassword.equals(newPasswordRepeat)) {
            model.addAttribute("newPasswordRepeatError", "Atkārtotā parole nav vienāda!");
            flag = true;
        }
        return flag;
    }

    public void updateUserPassword(User user, String password) {
        final String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registerUser(User user) {
        final String encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with that email not found");
        }
    }
}
