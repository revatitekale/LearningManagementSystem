package com.bl.lms.service;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.Response;

public interface ICandidateBankDetails {
    Response updateCandidateBankInfo(CandidateBankDetailsDTO candidateBankDetailsDTO);
}
