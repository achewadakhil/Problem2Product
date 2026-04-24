package com.backend.DTO.request;

import com.backend.entity.ProblemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProblemRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 4000) String description,
        ProblemStatus status) {
}
