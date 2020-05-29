package com.bl.lms.service;

import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.Response;

public interface ICandidateQualification {
    Response updateQualificationDetails(CandidateQualificationDTO candidateQualificationDTO);
}
