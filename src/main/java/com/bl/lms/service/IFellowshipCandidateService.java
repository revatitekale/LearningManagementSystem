package com.bl.lms.service;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.Candidate;
import com.bl.lms.model.CandidateBankDetails;
import com.bl.lms.model.CandidateQualification;
import com.bl.lms.model.FellowshipCandidate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;

public interface IFellowshipCandidateService {

    FellowshipCandidate joinCandidateToFellowship(long id) throws MessagingException;
    int getCandidateCount();
    FellowshipCandidate updateInformation(FellowshipCandidateDTO fellowshipCandidateDto);
    void sendMail(Candidate candidate) throws MessagingException;
    CandidateBankDetails updateBankDetails(CandidateBankDetails bankDetailsDto);
    CandidateQualification updateQualificationDetails(CandidateQualificationDTO candidateQualificationDto);
    String uploadDocuments(MultipartFile file, String updateDocumentDto);
    boolean upload(MultipartFile file, long id) throws LmsAppException, IOException;
    File convertMultiPartToFile(MultipartFile file) throws IOException;
}
