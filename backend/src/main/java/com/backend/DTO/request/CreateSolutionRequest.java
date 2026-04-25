package com.backend.DTO.request;

import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateSolutionRequest(
        @Size(max = 255) String githubLink,
        @Size(max = 255) String deployedLink,
        @Size(max = 4000) String summary,
        List<Long> teamMemberIds) {
}
