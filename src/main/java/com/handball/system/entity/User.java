package com.handball.system.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vārds nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    private String name;
    @NotBlank(message = "Uzvārds nevar būt tukšs!")
    @Size(max = 250, message = "Maksimālais simbolu skaits ir 250!")
    private String surname;

    @Column(unique = true)
    @NotBlank(message = "E-pasts nevar būt tukšs!")
    @Email(message = "Ievadiet pareizu e-pastu!")
    @Size(max = 100, message = "Maksimālais simbolu skaits ir 100!")
    private String email;
    @NotNull(message = "Parole nevar būt tukša!")
    @Size(min = 8, message = "Minimālais paroles garums ir 8 simboli!")
    @Size(max = 80, message = "Maksimālais paroles garums ir 80 simboli!")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(String role) {
        Role selectedRole;
        switch (role) {
            case "ADMIN":
                selectedRole = Role.ADMIN;
                break;
            case "MANAGER":
                selectedRole = Role.MANAGER;
                break;
            case "PROTOCOLIST":
                selectedRole = Role.PROTOCOLIST;
                break;
            case "USER":
                selectedRole = Role.USER;
                break;
            case "ORGANIZER":
                selectedRole = Role.ORGANIZER;
                break;
            default:
                selectedRole = null;
        }
        return this.roles.contains(selectedRole);
    }
}
