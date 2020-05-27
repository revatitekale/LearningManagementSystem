package com.bl.lms.service;

import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;

public interface IFellowshipCandidateService {

    Response joinCandidateToFellowship(FellowshipCandidateDTO fellowshipCandidateDTO);
    Response getCandidateCount();
}
