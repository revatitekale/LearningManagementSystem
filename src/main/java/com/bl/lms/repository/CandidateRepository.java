package com.bl.lms.repository;

import com.bl.lms.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CandidateRepository  extends JpaRepository<Candidate, Integer> {
    Candidate findByFirst_name(String first_name);
}
