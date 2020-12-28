package com.handball.system.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void getAuthority() {
        Role role = Role.MANAGER;
        assertEquals("MANAGER", role.getAuthority());
    }
}