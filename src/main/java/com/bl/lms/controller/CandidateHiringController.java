package com.bl.lms.controller;

import com.bl.lms.dto.CandidateProfileRequestDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.Candidate;
import com.bl.lms.service.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping("/candidatehiring")
public class CandidateHiringController {

    @Autowired
    ICandidateService candidateService;

    @PostMapping("/importcandidates")
    public Response addHiredCandidate(@RequestParam(value = "filePath") MultipartFile filePath) throws IOException {
        //String filepath="./src/main/java/resources/hired_candidate.xlsx";
        List hiredCandidate = candidateService.getHiredCandidate(filePath);
        candidateService.saveCandidateDetails(hiredCandidate);
        return new Response(200, "Successfully Added");
    }

    @GetMapping("/list")
    public List listHiredCandidate() throws IOException {
        return candidateService.getHiredCandidates();
    }

    @GetMapping("/showprofile")
    public Candidate showCandidateProfile(@RequestBody CandidateProfileRequestDTO viewProfileRequest) throws IOException {
        return candidateService.showByFirstName(viewProfileRequest.getFirst_name());
    }
}
