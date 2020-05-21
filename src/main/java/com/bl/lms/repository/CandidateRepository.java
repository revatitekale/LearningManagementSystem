package com.bl.lms.repository;

import com.bl.lms.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("candidateRepository")
public interface CandidateRepository  extends JpaRepository<Candidate, Integer> {
    @Query("select h from hired_candidate h where h.first_name = ?1")
    Candidate findByFirstName(String first_name);
}
