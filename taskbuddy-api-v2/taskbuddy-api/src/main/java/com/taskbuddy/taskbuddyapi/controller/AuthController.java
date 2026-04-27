package com.taskbuddy.taskbuddyapi.controller;

import com.taskbuddy.taskbuddyapi.config.JwtUtil;
import com.taskbuddy.taskbuddyapi.dto.AuthDtos;
import com.taskbuddy.taskbuddyapi.model.User;
import com.taskbuddy.taskbuddyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * AuthController — handles user registration and login.
 *
 * POST /api/auth/register  → create new account
 * POST /api/auth/login     → returns JWT token
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ─────────────────────────────────────────
    // POST /api/auth/register
    // Body: { name, email, password }
    // ─────────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDtos.RegisterRequest req) {

        // Check if email already exists
        if (userRepository.existsByEmail(req.email)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already in use"));
        }

        // Create and save user with hashed password
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));
        User saved = userRepository.save(user);

        // Generate token and return
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthDtos.AuthResponse(token, saved.getId(), saved.getName(), saved.getEmail()));
    }

    // ─────────────────────────────────────────
    // POST /api/auth/login
    // Body: { email, password }
    // ─────────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.LoginRequest req) {

        Optional<User> userOpt = userRepository.findByEmail(req.email);

        // Check user exists and password matches
        if (userOpt.isEmpty() || !passwordEncoder.matches(req.password, userOpt.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return ResponseEntity.ok(
                new AuthDtos.AuthResponse(token, user.getId(), user.getName(), user.getEmail()));
    }
}
