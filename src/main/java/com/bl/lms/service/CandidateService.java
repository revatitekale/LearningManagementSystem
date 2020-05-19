package com.bl.lms.service;

import com.bl.lms.model.Candidate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface CandidateService {

    List getHiredCandidate(String filePath) throws IOException;

    void saveCandidateDetails(List sheetData);

    List getHiredCandidates();

    Candidate findByFirst_name(String name);
}
