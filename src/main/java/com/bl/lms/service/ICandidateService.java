package com.bl.lms.service;

import com.bl.lms.dto.CandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.Candidate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface ICandidateService {

    boolean getHiredCandidate(MultipartFile filePath) throws IOException;

    void saveCandidateDetails(CandidateDTO candidateDTO) throws MessagingException;

    void sendEmail(CandidateDTO candidateDTO) throws MessagingException;

    List getHiredCandidatesList();

    Optional<Candidate> showById(Long id);

    Candidate updateCandidateStatus(String response, String email) throws LmsAppException;
}