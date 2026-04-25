package com.backend.DTO.response;

public record AuthResponse(
        String token,
        String tokenType,
        Long userId,
        String username,
        String email,
        String role) {
}
