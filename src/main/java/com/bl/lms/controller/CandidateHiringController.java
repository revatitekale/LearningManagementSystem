package com.bl.lms.controller;

import com.bl.lms.configuration.ApplicationConfig;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.Candidate;
import com.bl.lms.service.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/candidatehiring")
public class CandidateHiringController {

    @Autowired
    ICandidateService candidateService;

    @PostMapping("/importcandidate")
    public ResponseEntity<Response> addHiredCandidate(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(candidateService.getHiredCandidate(file));
    }

    @GetMapping("/list")
    public List listHiredCandidateList() {
        return candidateService.getHiredCandidatesList();
    }

    @GetMapping("/showprofile")
    public ResponseEntity<Candidate> showCandidateProfile(@PathVariable long id) {
        return ResponseEntity.ok(candidateService.showById(id));
    }

    @PutMapping("/updatestatus")
    public ResponseEntity<Response> updateCandidateStatus(@RequestParam String response, @RequestParam String email) throws LmsAppException {
        return ResponseEntity.ok(candidateService.updateCandidateStatus(response, email));
    }

    @GetMapping("/joboffer")
    public ResponseEntity<Response> sendJobOfferNotification() throws MessagingException {
        return ResponseEntity.ok(candidateService.sendJobOffer());
    }
}