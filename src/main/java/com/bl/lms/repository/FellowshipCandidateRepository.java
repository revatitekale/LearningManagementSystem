package com.bl.lms.repository;

import com.bl.lms.model.FellowshipCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FellowshipCandidateRepository extends JpaRepository<FellowshipCandidate, Integer> {

    Optional<FellowshipCandidate> findById(long id);
}
