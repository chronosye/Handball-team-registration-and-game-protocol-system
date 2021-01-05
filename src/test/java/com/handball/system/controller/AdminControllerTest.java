package com.handball.system.controller;

import com.handball.system.TestData;
import com.handball.system.entity.User;
import com.handball.system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    Model model;

    @Mock
    UserService userService;

    @Mock
    ResponseStatusException responseStatusException;

    private User user;

    @BeforeEach
    void setUp() {
        user = TestData.getUser2();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void adminPanel() {
        assertEquals("admin/admin", adminController.adminPanel());
    }

    @Test
    void showUsers() {
        assertEquals("admin/editRoles", adminController.showUsers(model));
    }

    @Test
    void addOrganizer() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        assertEquals("redirect:/admin/editRoles", adminController.addOrganizer("2"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/admin/editRoles/addOrganizer/2")).andExpect(status().isNotFound());
    }

    @Test
    void removeOrganizer() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        assertEquals("redirect:/admin/editRoles", adminController.removeOrganizer("2"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/admin/editRoles/removeOrganizer/2")).andExpect(status().isNotFound());
    }

    @Test
    void addManager() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        assertEquals("redirect:/admin/editRoles", adminController.addManager("2"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/admin/editRoles/addManager/2")).andExpect(status().isNotFound());
    }

    @Test
    void removeManager() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        assertEquals("redirect:/admin/editRoles", adminController.removeManager("2"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/admin/editRoles/removeManager/2")).andExpect(status().isNotFound());
    }

    @Test
    void addProtocolist() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        assertEquals("redirect:/admin/editRoles", adminController.addProtocolist("2"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/admin/editRoles/addProtocolist/2")).andExpect(status().isNotFound());
    }

    @Test
    void removeProtocolist() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        assertEquals("redirect:/admin/editRoles", adminController.removeProtocolist("2"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/admin/editRoles/removeProtocolist/2")).andExpect(status().isNotFound());
    }
}