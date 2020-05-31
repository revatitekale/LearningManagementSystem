package com.bl.lms.controller;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.service.IFellowshipCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fellowshipdetails")
public class FellowshipCandidateController {

    @Autowired
    private IFellowshipCandidateService fellowshipCandidateService;

    @PostMapping("/joincandidate")
    public ResponseEntity<Response> joinCandidate(@RequestBody FellowshipCandidateDTO fellowshipCandidateDTO) {
        return ResponseEntity.ok(fellowshipCandidateService.joinCandidateToFellowship(fellowshipCandidateDTO));
    }

    @GetMapping("/getcount")
    public ResponseEntity<Response> getCandidateCount() {
        return ResponseEntity.ok(fellowshipCandidateService.getCandidateCount());
    }

    @PostMapping("/updatequalificationdetails")
    public ResponseEntity<Response> updateCandidateQualificationInfo (@RequestBody CandidateQualificationDTO candidateQualificationDTO) {
        return ResponseEntity.ok(fellowshipCandidateService.updateQualificationDetails(candidateQualificationDTO));
    }

    @PostMapping("/updatebankdetails")
    public ResponseEntity<Response> updateCandidateBankInfo(@RequestBody CandidateBankDetailsDTO candidateBankDetailsDTO) {
        return ResponseEntity.ok(fellowshipCandidateService.updateCandidateBankInfo(candidateBankDetailsDTO));
    }

}
