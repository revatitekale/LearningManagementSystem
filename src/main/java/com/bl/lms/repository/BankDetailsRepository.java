package com.bl.lms.repository;

import com.bl.lms.model.CandidateBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDetailsRepository extends JpaRepository<CandidateBankDetails, Long> {
}
