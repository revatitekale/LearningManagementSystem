package com.bl.lms.controller;

import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.service.ICandidateQualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidatequalification")
public class StudentsQualificationController {

    @Autowired
    ICandidateQualification candidateQualification;

    @PostMapping("/updatequalificationdetails")
    public ResponseEntity<Response> updateCandidateQualificationInfo (@RequestBody CandidateQualificationDTO candidateQualificationDTO) {
        return ResponseEntity.ok(candidateQualification.updateQualificationDetails(candidateQualificationDTO));
    }
}
