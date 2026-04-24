package com.backend.service;

import com.backend.DTO.request.LoginRequest;
import com.backend.DTO.request.RegisterRequest;
import com.backend.DTO.response.AuthResponse;
import com.backend.DTO.response.UserResponse;
import com.backend.entity.UserAccount;
import com.backend.entity.UserProfile;
import com.backend.repository.UserProfileRepository;
import com.backend.repository.UserRepository;
import com.backend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        if (userRepository.existsByEmail(request.email().trim().toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        UserAccount newUser = new UserAccount();
        newUser.setUsername(request.username().trim());
        newUser.setEmail(request.email().trim().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole("ROLE_USER");


        UserAccount user = userRepository.save(newUser);

        UserProfile profile = new UserProfile();
        profile.setUser(user);

        userProfileRepository.save(profile);
        return buildAuthResponse(newUser);
    }

    public AuthResponse login(LoginRequest request) {
        UserAccount user = userRepository.findByUsername(request.username().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        return buildAuthResponse(user);
    }

    public UserResponse me(UserDetails principal) {
        UserAccount user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }

    private AuthResponse buildAuthResponse(UserAccount user) {
        return new AuthResponse(
                jwtService.generateToken(user.getUsername()),
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }
}
