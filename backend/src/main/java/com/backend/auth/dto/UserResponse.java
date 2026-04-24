package com.backend.auth.dto;

public record UserResponse(Long id, String username, String email, String role) {
}
