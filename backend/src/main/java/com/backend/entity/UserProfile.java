package com.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Link with UserAccount
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserAccount user;

    // 👤 Basic Info
    private String fullName;

    @Column(length = 300)
    private String bio;

    // 🎓 Academic
    private String college;
    private String department;
    private Integer year;

    // 💻 Skills
    private String skills;      // comma separated
    private String techStack;   // comma separated

    // 📊 Contribution stats
    private int problemsPosted = 0;
    private int solutionsSubmitted = 0;
    private int acceptedSolutions = 0;
    private int reputation = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public int getProblemsPosted() {
        return problemsPosted;
    }

    public void setProblemsPosted(int problemsPosted) {
        this.problemsPosted = problemsPosted;
    }

    public int getSolutionsSubmitted() {
        return solutionsSubmitted;
    }

    public void setSolutionsSubmitted(int solutionsSubmitted) {
        this.solutionsSubmitted = solutionsSubmitted;
    }

    public int getAcceptedSolutions() {
        return acceptedSolutions;
    }

    public void setAcceptedSolutions(int acceptedSolutions) {
        this.acceptedSolutions = acceptedSolutions;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    // 🔗 Social Links
    private String github;
    private String linkedin;
    private String portfolio;

    // Getters & Setters
}