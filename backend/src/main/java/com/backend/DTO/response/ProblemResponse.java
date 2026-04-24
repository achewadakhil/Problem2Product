package com.backend.DTO.response;

import com.backend.entity.ProblemStatus;
import java.time.Instant;
import java.util.List;

public record ProblemResponse(
        Long id,
        ProblemUserResponse postedBy,
        String title,
        String description,
        ProblemStatus status,
        List<SolutionResponse> solutions,
        Instant createdAt,
        Instant updatedAt) {
}
