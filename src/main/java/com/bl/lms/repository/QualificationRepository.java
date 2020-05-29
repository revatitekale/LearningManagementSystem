package com.bl.lms.repository;

import com.bl.lms.model.CandidateQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepository extends JpaRepository<CandidateQualification, Long> {
}
