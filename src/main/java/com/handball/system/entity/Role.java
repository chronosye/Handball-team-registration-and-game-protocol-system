package com.handball.system.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,ORGANIZER,MANAGER,PROTOCOLIST,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
