package com.backend.DTO.response;

import java.time.Instant;
import java.util.List;

public record SolutionResponse(
        Long id,
        ProblemUserResponse submittedBy,
        List<ProblemUserResponse> teamMembers,
        String githubLink,
        String deployedLink,
        String summary,
        Instant createdAt) {
}
