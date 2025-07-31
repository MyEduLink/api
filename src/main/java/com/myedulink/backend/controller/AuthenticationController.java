package com.myedulink.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.myedulink.backend.dto.AuthRequest;
import com.myedulink.backend.dto.AuthResponse;
import com.myedulink.backend.dto.UserRegisterDto;
import com.myedulink.backend.model.User;
import com.myedulink.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> register(@RequestBody User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(user));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> me(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(401).body("Authentication is null");
        }

        Object principal = auth.getPrincipal();
        return ResponseEntity.ok(principal);
    }
}
