package com.handball.system.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @InjectMocks
    LoginController loginController;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login() {
        assertEquals("login", loginController.login());
    }

    @Test
    void loginError() {
        assertEquals("login", loginController.loginError(model));
    }
}