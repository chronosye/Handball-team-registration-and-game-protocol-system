package com.handball.system.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @Mock
    BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        player = new Player();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getId() {
        player.setId(3L);
        assertEquals(3L, player.getId());
    }

    @Test
    void getName() {
        player.setName("TEST");
        assertEquals("TEST", player.getName());
    }

    @Test
    void getSurname() {
        player.setSurname("123123");
        assertEquals("123123", player.getSurname());
    }

    @Test
    void getPosition() {
        player.setPosition("GK");
        assertEquals("GK", player.getPosition());
    }

    @Test
    void getTeam() {
        Team team = new Team();
        player.setTeam(team);
        assertEquals(team, player.getTeam());
    }

    @Test
    void validatePlayerForm() {
        player.setName("");
        player.validatePlayerForm(bindingResult);
    }
}