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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    UserController userController;

    private User user;
    private User formUser;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        formUser = TestData.getUser();
        formUser.setId(2L);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void showProfile() {
        assertEquals("user/profile", userController.showProfile(model, user));
    }

    @Test
    void editProfileData() {
        assertEquals("user/dataForm", userController.editProfileData(model, user));
    }

    @Test
    void postEditProfileData() {
        assertEquals("redirect:/profile", userController.editProfileData(formUser, bindingResult, user));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("user/dataForm", userController.editProfileData(formUser, bindingResult, user));
        when(userService.userExists(anyString())).thenReturn(true);
        formUser.setEmail("TESTTEST@TEST");
        assertEquals("user/dataForm", userController.editProfileData(formUser, bindingResult, user));
    }

    @Test
    void editProfilePassword() {
        assertEquals("user/passwordForm", userController.editProfilePassword(model, user));
    }

    @Test
    void postEditProfilePassword() {
        when(userService.validatePasswordChange(any(User.class), anyString(), anyString(), anyString(), any())).thenReturn(false);
        assertEquals("redirect:/profile", userController.editProfilePassword(model, user, user.getPassword(), user.getPassword(), user.getPassword()));
        when(userService.validatePasswordChange(any(User.class), anyString(), anyString(), anyString(), any())).thenReturn(true);
        assertEquals("user/passwordForm", userController.editProfilePassword(model, user, user.getPassword(), "12345678", "12345678"));
    }
}