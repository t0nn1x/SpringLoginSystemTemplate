package com.loginsystem.authenticated_backend.controller;

import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginsystem.authenticated_backend.dto.LoginResponseDTO;
import com.loginsystem.authenticated_backend.dto.RegistrationDTO;
import com.loginsystem.authenticated_backend.model.ApplicationUser;
import com.loginsystem.authenticated_backend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationContoller {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body, HttpServletResponse response) {
        LoginResponseDTO loginResponse = authenticationService.loginUser(body.getUsername(), body.getPassword());

        // Generate a random CSRF token
        String csrfToken = UUID.randomUUID().toString();

        // Set the CSRF token in a cookie
        Cookie csrfCookie = new Cookie("CSRF-TOKEN", csrfToken);
        response.addCookie(csrfCookie);

        // Also send the CSRF token in the response header or body
        response.setHeader("X-CSRF-TOKEN", csrfToken);

        return loginResponse;
    }
}
