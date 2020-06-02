package com.bl.lms.controller;

import com.bl.lms.configuration.ApplicationConfig;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.Candidate;
import com.bl.lms.service.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidatehiring")
public class CandidateHiringController {

    @Autowired
    ICandidateService candidateService;

    /**
     * @param file
     * @return isAdded
     * @throws IOException
     */
    @PostMapping("/importcandidate")
    public ResponseEntity<Response> addHiredCandidate(@RequestParam("file") MultipartFile file) throws IOException {
        boolean isAdded = candidateService.getHiredCandidate(file);
        return new  ResponseEntity<Response>(new  Response(isAdded, 200, ApplicationConfig.getMessageAccessor().getMessage("109")), HttpStatus.CREATED);
    }

    /**
     * @return list
     */
    @GetMapping("/list")
    public ResponseEntity<List> listHiredCandidateList() {
        List candidateList = candidateService.getHiredCandidatesList();
        return new ResponseEntity<List>(candidateList, HttpStatus.CREATED);
    }

    /**
     * @param candidateId
     * @return Candidate profile by their ID
     */
    @GetMapping("/showprofile")
    public ResponseEntity<Response> showCandidateProfile(@RequestParam(value = "id") long candidateId) {
        Optional<Candidate> hiredCandidate = candidateService.showById(candidateId);
        return new ResponseEntity<Response>(new Response(hiredCandidate, 200, ApplicationConfig.getMessageAccessor().getMessage("105")), HttpStatus.OK);
    }

    /**
     *
     * @param email
     * @param status
     * @return hiredCandidateModel
     */
    @PutMapping("/updatestatus")
    public ResponseEntity<Response> updateCandidateStatus(@RequestParam(value = "email")  String email, @RequestParam(value = "status") String status){
        Candidate candidateModel = candidateService.updateCandidateStatus(email, status);
        return new ResponseEntity<>(new Response(candidateModel, 200 ,ApplicationConfig.getMessageAccessor().getMessage("110")), HttpStatus.OK);
    }
}