package com.backend.service;

import com.backend.entity.UserAccount;
import com.backend.entity.UserProfile;
import com.backend.repository.UserProfileRepository;
import com.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository,
                              UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    // ✅ GET PROFILE
    public UserProfile getProfile(Authentication authentication) {
        String username = authentication.getName();

        UserAccount user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    // ✅ UPDATE PROFILE
    public UserProfile updateProfile(Authentication authentication, UserProfile updatedProfile) {
        String username = authentication.getName();

        UserAccount user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // update only profile fields (NOT user account fields)
        profile.setFullName(updatedProfile.getFullName());
        profile.setBio(updatedProfile.getBio());
        profile.setCollege(updatedProfile.getCollege());
        profile.setDepartment(updatedProfile.getDepartment());
        profile.setYear(updatedProfile.getYear());
        profile.setSkills(updatedProfile.getSkills());
        profile.setTechStack(updatedProfile.getTechStack());
        profile.setGithub(updatedProfile.getGithub());
        profile.setLinkedin(updatedProfile.getLinkedin());
        profile.setPortfolio(updatedProfile.getPortfolio());

        return userProfileRepository.save(profile);
    }
}