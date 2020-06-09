package com.bl.lms.repository;

import com.bl.lms.model.CandidateBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component("bankDetailsRepository")
public interface BankDetailsRepository extends JpaRepository<CandidateBankDetails, Long> {
    Optional<CandidateBankDetails> findById(long id);
}
