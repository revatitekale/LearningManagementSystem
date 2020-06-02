package com.bl.lms.repository;

import com.bl.lms.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component("candidateRepository")
public interface CandidateRepository  extends JpaRepository<Candidate, Long> {
    List<Candidate> findAll();

    Optional<Candidate> findById(long candidateId);

    Optional<Candidate> findByEmail(String email);
}
