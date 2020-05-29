package com.bl.lms.controller;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.service.ICandidateBankDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankdetails")
public class CandidateBankDetailsController {

    @Autowired
    ICandidateBankDetails candidateBankDetails;

    @PostMapping("/updatebankdetails")
    public ResponseEntity<Response> updateCandidateBankInfo(@RequestBody CandidateBankDetailsDTO candidateBankDetailsDTO) {
        return ResponseEntity.ok(candidateBankDetails.updateCandidateBankInfo(candidateBankDetailsDTO));
    }
}
