package com.bl.lms.controller;

import com.bl.lms.dto.CandidateProfileRequest;
import com.bl.lms.dto.Response;
import com.bl.lms.model.Candidate;
import com.bl.lms.service.CandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("candidatehiring")
public class CandidateHiringController {

    @Autowired
    CandidateServiceImpl candidateService;

    @PostMapping("/importcandidate")
    public Response addHiredCandidate() throws IOException {
        String filePath = "C:\\Users\\Revati Tekale\\Documents\\hired_candidate.xlsx";
        List hiredCandidate = candidateService.getHiredCandidate(filePath);
        candidateService.saveCandidateDetails(hiredCandidate);
        return new Response(200, "Successfully Added");
    }

    @GetMapping("/list")
    public List listHiredCandidate() throws IOException {
        return candidateService.getHiredCandidates();
    }

    @GetMapping("/showprofile")
    public Candidate showCandidateProfile(@RequestBody CandidateProfileRequest viewProfileRequest) throws IOException {
        return candidateService.findByFirst_name(viewProfileRequest.getFirst_name());
    }
}
