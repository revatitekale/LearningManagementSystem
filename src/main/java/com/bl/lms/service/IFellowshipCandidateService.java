package com.bl.lms.service;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.CandidateBankDetails;
import com.bl.lms.model.CandidateQualification;
import com.bl.lms.model.FellowshipCandidate;

import javax.mail.MessagingException;

public interface IFellowshipCandidateService {

    FellowshipCandidate joinCandidateToFellowship(long id);
    int getCandidateCount();
    FellowshipCandidate updateInformation(FellowshipCandidateDTO fellowshipCandidateDto);
    void sendMail(FellowshipCandidate fellowshipCandidateModel) throws MessagingException;
    CandidateBankDetails updateBankDetails(CandidateBankDetails bankDetailsDto);
    CandidateQualification updateQualificationDetails(CandidateQualificationDTO candidateQualificationDto)
}
