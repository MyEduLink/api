package com.myedulink.backend.services;

import com.myedulink.backend.config.JwtTokenUtil;
import com.myedulink.backend.config.JwtUserDetailsService;
import com.myedulink.backend.dto.AuthRequest;
import com.myedulink.backend.dto.AuthResponse;
import com.myedulink.backend.dto.UserRegisterDto;
import com.myedulink.backend.model.User;
import com.myedulink.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService jwtUserDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail().toLowerCase(), request.getPassword()
                )
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());
        return new AuthResponse(token);
    }

    public UserRegisterDto register(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() ||
            user.getPassword() == null || user.getPassword().isBlank() ||
            user.getFullName() == null || user.getFullName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name, email and password are required");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        return new UserRegisterDto(userRepository.save(user));
    }
}
