package com.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "problem_solutions")
public class ProblemSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by_user_id", nullable = false)
    private UserAccount submittedBy;

    @ManyToMany
    @JoinTable(
            name = "solution_team_members",
            joinColumns = @JoinColumn(name = "solution_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
        private Set<UserAccount> teamMembers = new HashSet<>();

    @Column(length = 255)
    private String githubLink;

    @Column(length = 255)
    private String deployedLink;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public UserAccount getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(UserAccount submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Set<UserAccount> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<UserAccount> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getDeployedLink() {
        return deployedLink;
    }

    public void setDeployedLink(String deployedLink) {
        this.deployedLink = deployedLink;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
