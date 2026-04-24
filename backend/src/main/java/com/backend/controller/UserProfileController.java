package com.backend.controller;

import com.backend.entity.UserProfile;
import com.backend.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // ✅ GET PROFILE
    @GetMapping
    public ResponseEntity<UserProfile> getProfile(Authentication authentication) {
        return ResponseEntity.ok(userProfileService.getProfile(authentication));
    }

    // ✅ UPDATE PROFILE
    @PutMapping
    public ResponseEntity<UserProfile> updateProfile(Authentication authentication,
                                                     @RequestBody UserProfile updatedProfile) {
        return ResponseEntity.ok(
                userProfileService.updateProfile(authentication, updatedProfile)
        );
    }
}