package com.bl.lms.service;

import com.bl.lms.dto.CandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.Candidate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;


public interface ICandidateService {

    Response getHiredCandidate(MultipartFile filePath) throws IOException;

    void saveCandidateDetails(CandidateDTO sheetData) throws MessagingException;

    List getHiredCandidatesList();

    Candidate showById(Long id);

    Response updateStatus(String response, String email);

    void sendMail(Candidate hiredCandidate) throws MessagingException;

    Response sendJobOffer() throws MessagingException;
}