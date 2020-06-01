package com.bl.lms.repository;

import com.bl.lms.model.CandidateDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateDocumentRepository extends JpaRepository<CandidateDocuments, Long> {
}
