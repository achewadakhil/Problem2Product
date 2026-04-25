package com.backend.repository;

import com.backend.entity.Problem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @EntityGraph(attributePaths = {"postedBy", "solutions", "solutions.submittedBy", "solutions.teamMembers"})
    List<Problem> findAllByOrderByCreatedAtDesc();

    @Override
    @EntityGraph(attributePaths = {"postedBy", "solutions", "solutions.submittedBy", "solutions.teamMembers"})
    Optional<Problem> findById(Long id);
}
