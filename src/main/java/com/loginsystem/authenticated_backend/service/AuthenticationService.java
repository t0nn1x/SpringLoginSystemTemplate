package com.loginsystem.authenticated_backend.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loginsystem.authenticated_backend.dto.LoginResponseDTO;
import com.loginsystem.authenticated_backend.model.ApplicationUser;
import com.loginsystem.authenticated_backend.model.Role;
import com.loginsystem.authenticated_backend.repository.RoleRepository;
import com.loginsystem.authenticated_backend.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public ApplicationUser registerUser(String username, String password) {

        String encodedPassword = passwordEncoder.encode(password);
        logger.info("Encoded password for user {}: {}", username, encodedPassword);

        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

    

    public LoginResponseDTO loginUser(String username, String password) {

        ApplicationUser user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            logger.info("Retrieved password for user {}: {}", username, user.getPassword());
        } else {
            logger.warn("User {} not found in the database.", username);
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJWT(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }
}
