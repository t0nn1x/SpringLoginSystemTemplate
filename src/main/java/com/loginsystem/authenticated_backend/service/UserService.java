package com.loginsystem.authenticated_backend.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.loginsystem.authenticated_backend.model.ApplicationUser;
import com.loginsystem.authenticated_backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }

    public String createUser(ApplicationUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already exists!";
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User created successfully";
        } catch (DataIntegrityViolationException e) {
            return "Error: Duplicate username or other integrity constraint violation";
        } catch (Exception e) {
            return "Error: Unable to create user";
        }
    }

}
