package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.User;
import com.handball.system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    @InjectMocks
    RegisterController registerController;

    @Mock
    Model model;

    @Mock
    UserService userService;

    @Mock
    BindingResult bindingResult;

    private User user;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void register() {
        assertEquals("register", registerController.register(user, model));
    }

    @Test
    void postRegister() {
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals("redirect:/login", registerController.register(user, bindingResult));
        when(userService.userExists(anyString())).thenReturn(true);
        assertEquals("register", registerController.register(user, bindingResult));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("register", registerController.register(user, bindingResult));
    }
}