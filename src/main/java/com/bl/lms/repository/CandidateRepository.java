package com.bl.lms.repository;

import com.bl.lms.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component("candidateRepository")
public interface CandidateRepository  extends JpaRepository<Candidate, Long> {
    Candidate findByEmail(String email);
    Candidate findByStatusAndId(String status, Long id);
    List<Candidate> findByStatus(String status);
}
