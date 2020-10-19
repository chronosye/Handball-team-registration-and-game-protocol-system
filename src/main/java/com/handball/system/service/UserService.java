package com.handball.system.service;

import com.handball.system.entity.User;
import com.handball.system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        userRepository.save(user);
        return user;
    }

    public boolean userExists(String email){
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id){
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        userRepository.delete(userToDelete);
    }
}
