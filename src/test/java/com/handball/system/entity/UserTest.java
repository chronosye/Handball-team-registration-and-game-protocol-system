package com.handball.system.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    BindingResult bindingResult;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
    }

    @Test
    void getId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void getName() {
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    void getSurname() {
        user.setSurname("ABCD");
        assertEquals("ABCD", user.getSurname());
    }

    @Test
    void getEmail() {
        user.setEmail("aa@aa.lv");
        assertEquals("aa@aa.lv", user.getEmail());
    }

    @Test
    void getPassword() {
        String password = "parole";
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        passwordEncoder.matches(password, user.getPassword());
    }

    @Test
    void getRoles() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.MANAGER);
        user.setRoles(roleSet);
        assertEquals(roleSet, user.getRoles());
    }

    @Test
    void hasRole() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ADMIN);
        roleSet.add(Role.MANAGER);
        roleSet.add(Role.ORGANIZER);
        roleSet.add(Role.USER);
        roleSet.add(Role.PROTOCOLIST);
        user.setRoles(roleSet);
        assertTrue(user.hasRole("ADMIN"));
        assertTrue(user.hasRole("MANAGER"));
        assertTrue(user.hasRole("ORGANIZER"));
        assertTrue(user.hasRole("USER"));
        assertTrue(user.hasRole("PROTOCOLIST"));
        assertFalse(user.hasRole("ABC"));
    }

    @Test
    void getAuthorities() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ADMIN);
        roleSet.add(Role.MANAGER);
        user.setRoles(roleSet);
        assertEquals(2, user.getAuthorities().size());
    }

    @Test
    void getUsername() {
        user.setEmail("aa@aa.lv");
        assertEquals("aa@aa.lv", user.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    void validateDataEditForm() {
        user.validateDataEditForm(bindingResult);
    }
}