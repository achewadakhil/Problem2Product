package com.backend.controller;

import com.backend.DTO.request.CreateProblemRequest;
import com.backend.DTO.request.CreateSolutionRequest;
import com.backend.DTO.response.ProblemResponse;
import com.backend.service.ProblemService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProblemResponse createProblem(
            @Valid @RequestBody CreateProblemRequest request,
            Authentication authentication) {
        return problemService.createProblem(request, authentication.getName());
    }

    @GetMapping
    public List<ProblemResponse> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/{problemId}")
    public ProblemResponse getProblemById(@PathVariable Long problemId) {
        return problemService.getProblemById(problemId);
    }

    @PostMapping("/{problemId}/solutions")
    public ProblemResponse addSolution(
            @PathVariable Long problemId,
            @Valid @RequestBody CreateSolutionRequest request,
            Authentication authentication) {
        return problemService.addSolution(problemId, request, authentication.getName());
    }
}
