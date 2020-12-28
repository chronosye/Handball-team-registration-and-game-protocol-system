package com.handball.system.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class userIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void register() throws Exception {
        this.mockMvc.perform(post("/register").param("name", "new")
                .param("surname", "newSurname")
                .param("email", "new@new")
                .param("password", "test123123123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void registerError() throws Exception {
        this.mockMvc.perform(post("/register").param("name", "new")
                .param("surname", "newSurname")
                .param("email", "admin@test")
                .param("password", "test123123123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "email"));
    }

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(formLogin("/login").user("admin@test").password("12345678"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void loginError() throws Exception {
        this.mockMvc.perform(formLogin("/login").user("admin@test").password("1321432"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-error"));
    }

    @Test
    @WithMockUser(username = "admin@test", password = "12345678", authorities = {"ADMIN"})
    public void getAdminPanelPage() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().isOk())
                .andExpect(view().name("admin/admin"));
    }

    @Test
    @WithMockUser(username = "user@test", password = "12345678", authorities = {"USER"})
    public void getAdminPanel403() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "org@test", password = "12345678", authorities = {"ORGANIZER"})
    public void getOrganizerPanelPage() throws Exception {
        this.mockMvc.perform(get("/organizer")).andExpect(status().isOk())
                .andExpect(view().name("organizer/organizer"));
    }

    @Test
    @WithMockUser(username = "user@test", password = "12345678", authorities = {"USER"})
    public void getOrganizerPanel403() throws Exception {
        this.mockMvc.perform(get("/organizer")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "man@test", password = "12345678", authorities = {"MANAGER"})
    public void getManagerPanelPage() throws Exception {
        this.mockMvc.perform(get("/manager")).andExpect(status().isOk())
                .andExpect(view().name("manager/manager"));
    }

    @Test
    @WithMockUser(username = "user@test", password = "12345678", authorities = {"USER"})
    public void getManagerPanel403() throws Exception {
        this.mockMvc.perform(get("/manager")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "pro@test", password = "12345678", authorities = {"PROTOCOLIST"})
    public void getProtocolistPanelPage() throws Exception {
        this.mockMvc.perform(get("/protocolist")).andExpect(status().isOk())
                .andExpect(view().name("protocolist/protocolist"));
    }

    @Test
    @WithMockUser(username = "user@test", password = "12345678", authorities = {"USER"})
    public void getProtocolistPanel403() throws Exception {
        this.mockMvc.perform(get("/protocolist")).andExpect(status().isForbidden());
    }

    @Test
    public void getAdminPanelWhenNotLoggedIn() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().is3xxRedirection());
    }

    @Test
    public void get404() throws Exception {
        this.mockMvc.perform(get("/abcde")).andExpect(status().isNotFound());
    }
}
