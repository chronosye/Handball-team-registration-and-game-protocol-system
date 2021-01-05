package com.handball.system.service;

import com.handball.system.TestData;
import com.handball.system.entity.*;
import com.handball.system.repository.GameRepository;
import com.handball.system.repository.TeamRepository;
import com.handball.system.repository.TournamentRepository;
import com.handball.system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.thymeleaf.util.StringUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    TournamentRepository tournamentRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    Model model;

    private User user;
    private Set<Game> games;
    private Game game;
    private Team team;
    private Set<Tournament> tournaments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = TestData.getUser();
        games = TestData.getGames();
        game = TestData.getGame();
        team = TestData.getTeam();
        tournaments = TestData.getTournaments();
    }

    @Test
    void findUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.findUserById(anyLong());
        verify(userRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void findAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        userService.findAllUsers();
        verify(userRepository, atLeastOnce()).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void findAllUsersByRole() {
        Set<User> users = new HashSet<>();
        users.add(user);
        when(userRepository.findUsersByRolesContaining(any(Role.class))).thenReturn(users);
        userService.findAllUsersByRole(Role.MANAGER);
        verify(userRepository, atLeastOnce()).findUsersByRolesContaining(any(Role.class));
    }

    @Test
    void addRole() {
        userService.addRole(user, any(Role.class));
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void removeRoleManager() {
        when(teamRepository.findByManager(any(User.class))).thenReturn(team);
        when(gameRepository.findAllByHomeTeamOrAwayTeam(any(Team.class), any(Team.class))).thenReturn(games);
        when(tournamentRepository.findAllByTeamsContaining(any(Team.class))).thenReturn(tournaments);
        userService.removeRole(user, Role.MANAGER);
        verify(gameRepository, atLeastOnce()).delete(any(Game.class));
        verify(tournamentRepository, atLeastOnce()).save(any(Tournament.class));
        verify(teamRepository, atLeastOnce()).deleteAllByManager(any(User.class));
    }

    @Test
    void removeRoleOrganizer() {
        userService.removeRole(user, Role.ORGANIZER);
        verify(tournamentRepository, atLeastOnce()).deleteAllByOrganizer(any(User.class));
    }

    @Test
    void removeRoleProtocolist() {
        when(gameRepository.findAllByProtocolist(any(User.class))).thenReturn(games);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.removeRole(user, Role.PROTOCOLIST);
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void updateUserData() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.updateUserData(user, user);
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void validatePasswordChange() {
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertTrue(userService.validatePasswordChange(user, "1", "1", "2", model));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        assertTrue(userService.validatePasswordChange(user, user.getPassword(), "1", "2", model));
        String longPass = StringUtils.repeat("A", 81);
        assertTrue(userService.validatePasswordChange(user, user.getPassword(), longPass, longPass, model));
        assertTrue(userService.validatePasswordChange(user, user.getPassword(), "123123123", "55555555555", model));
    }

    @Test
    void updateUserPassword() {
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.updateUserPassword(user, "adsadasdsadas");
        verify(passwordEncoder, atLeastOnce()).encode(anyString());
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void userExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertTrue(userService.userExists(user.getEmail()));
    }

    @Test
    void registerUser() {
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.registerUser(user);
        verify(passwordEncoder, atLeastOnce()).encode(anyString());
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        userService.loadUserByUsername(user.getEmail());
        verify(userRepository, atLeastOnce()).findByEmail(anyString());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(null));
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(user.getEmail()));
    }
}