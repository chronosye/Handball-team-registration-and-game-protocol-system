package com.handball.system.service;

import com.handball.system.entity.Role;
import com.handball.system.entity.User;
import com.handball.system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Set<User> findAllUsers() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    public Set<User> findAllUsersByRole(Role role) {
        Set<User> users = new HashSet<>();
        userRepository.findUsersByRolesContaining(role).forEach(users::add);
        return users;
    }

    public void addRole(Long id, Role role) {
        Optional<User> user = userRepository.findById(id);
        User foundUser;
        foundUser = user.get();
        Set<Role> roles = foundUser.getRoles();
        roles.add(role);
        foundUser.setRoles(roles);
        userRepository.save(foundUser);
    }

    public void removeRole(Long id, Role role) {
        Optional<User> user = userRepository.findById(id);
        User foundUser;
        foundUser = user.get();
        Set<Role> roles = foundUser.getRoles();
        roles.remove(role);
        foundUser.setRoles(roles);
        userRepository.save(foundUser);
    }

    public User updateUser(User user) {
        userRepository.save(user);
        return user;
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        userRepository.delete(userToDelete);
    }

    public void registerUser(User user) {
        final String encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        final User createdUser = userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with that email not found");
        }
    }
}
