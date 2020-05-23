package com.bl.lms.service;

import com.bl.lms.model.Candidate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ICandidateService {

    List getHiredCandidate(MultipartFile filePath) throws IOException;

    void saveCandidateDetails(List sheetData);

    List getHiredCandidates();

    Candidate showByFirstName(String name);
}
