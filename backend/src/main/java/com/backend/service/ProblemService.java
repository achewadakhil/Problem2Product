package com.backend.service;

import com.backend.DTO.request.CreateProblemRequest;
import com.backend.DTO.request.CreateSolutionRequest;
import com.backend.DTO.response.ProblemResponse;
import com.backend.DTO.response.ProblemUserResponse;
import com.backend.DTO.response.SolutionResponse;
import com.backend.entity.Problem;
import com.backend.entity.ProblemSolution;
import com.backend.entity.ProblemStatus;
import com.backend.entity.UserAccount;
import com.backend.repository.ProblemRepository;
import com.backend.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    public ProblemService(ProblemRepository problemRepository, UserRepository userRepository) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProblemResponse createProblem(CreateProblemRequest request, String username) {
        UserAccount postedBy = getUserByUsername(username);

        Problem problem = new Problem();
        problem.setPostedBy(postedBy);
        problem.setTitle(request.title().trim());
        problem.setDescription(request.description().trim());
        problem.setStatus(request.status() == null ? ProblemStatus.OPEN : request.status());

        Problem saved = problemRepository.save(problem);
        return toProblemResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProblemResponse> getAllProblems() {
        return problemRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toProblemResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProblemResponse getProblemById(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
        return toProblemResponse(problem);
    }

    @Transactional
    public ProblemResponse addSolution(Long problemId, CreateSolutionRequest request, String username) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));

        UserAccount submittedBy = getUserByUsername(username);
        Set<UserAccount> teamMembers = new HashSet<>(resolveTeamMembers(request.teamMemberIds()));

        if (teamMembers.stream().noneMatch(member -> member.getId().equals(submittedBy.getId()))) {
            teamMembers.add(submittedBy);
        }

        ProblemSolution solution = new ProblemSolution();
        solution.setProblem(problem);
        solution.setSubmittedBy(submittedBy);
        solution.setTeamMembers(teamMembers);
        solution.setGithubLink(trimToNull(request.githubLink()));
        solution.setDeployedLink(trimToNull(request.deployedLink()));
        solution.setSummary(trimToNull(request.summary()));

        problem.getSolutions().add(solution);
        Problem saved = problemRepository.save(problem);
        return toProblemResponse(saved);
    }

    private UserAccount getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private List<UserAccount> resolveTeamMembers(List<Long> teamMemberIds) {
        if (teamMemberIds == null || teamMemberIds.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> uniqueIds = new HashSet<>(teamMemberIds);
        List<UserAccount> users = userRepository.findAllById(uniqueIds);
        if (users.size() != uniqueIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more team members do not exist");
        }
        return users;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private ProblemResponse toProblemResponse(Problem problem) {
        return new ProblemResponse(
                problem.getId(),
                toUserResponse(problem.getPostedBy()),
                problem.getTitle(),
                problem.getDescription(),
                problem.getStatus(),
                problem.getSolutions().stream().map(this::toSolutionResponse).collect(Collectors.toList()),
                problem.getCreatedAt(),
                problem.getUpdatedAt());
    }

    private SolutionResponse toSolutionResponse(ProblemSolution solution) {
        return new SolutionResponse(
                solution.getId(),
                toUserResponse(solution.getSubmittedBy()),
                solution.getTeamMembers().stream().map(this::toUserResponse).collect(Collectors.toList()),
                solution.getGithubLink(),
                solution.getDeployedLink(),
                solution.getSummary(),
                solution.getCreatedAt());
    }

    private ProblemUserResponse toUserResponse(UserAccount user) {
        return new ProblemUserResponse(user.getId(), user.getUsername(), user.getRole());
    }
}
