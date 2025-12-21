package com.learn.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(String username, String password) {
        Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if(auth.isAuthenticated()){
            return jwtService.generateToken(username);
        }
        else{
            return "Bad credentials";
        }
    }
}

