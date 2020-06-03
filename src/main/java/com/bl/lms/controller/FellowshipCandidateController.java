package com.bl.lms.controller;

import com.bl.lms.configuration.ApplicationConfig;
import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.CandidateBankDetails;
import com.bl.lms.model.CandidateQualification;
import com.bl.lms.model.FellowshipCandidate;
import com.bl.lms.service.IFellowshipCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/fellowshipdetails")
public class FellowshipCandidateController {

    @Autowired
    private IFellowshipCandidateService fellowshipCandidateService;

    /**
     * @param id
     * @return response(join the candidate to Fellowship)
     * @throws MessagingException
     */
    @PostMapping("/joincandidate")
    public ResponseEntity<Response> joinCandidate(@RequestParam(value = "id") long id) throws MessagingException {
        FellowshipCandidate fellowshipCandidate = fellowshipCandidateService.joinCandidateToFellowship(id);
        return new ResponseEntity<>(new Response(fellowshipCandidate, 200, ApplicationConfig.getMessageAccessor().getMessage("111")), HttpStatus.OK);
    }

    /**
     * @return response(Number of candidate)
     */
    @GetMapping("/getcount")
    public ResponseEntity<Response> getCandidateCount() {
        int candidateCount = fellowshipCandidateService.getCandidateCount();
        return new ResponseEntity<>(new Response(candidateCount, 200, ApplicationConfig.getMessageAccessor().getMessage("112")), HttpStatus.OK);
    }

    /**
     * @param fellowshipCandidateDto
     * @return response(Updated candidate personal information)
     */
    @PutMapping("/updateinformation")
    public ResponseEntity<Response> updatePersonalInformation(@Valid @RequestBody FellowshipCandidateDTO fellowshipCandidateDto) {
        FellowshipCandidate fellowshipCandidateModel = fellowshipCandidateService.updateInformation(fellowshipCandidateDto);
        return new ResponseEntity<>(new Response(fellowshipCandidateModel, 200, ApplicationConfig.getMessageAccessor().getMessage("111")), HttpStatus.OK);
    }

    /**
     * @param candidateBankDetailsDto
     * @return response(Updated bank details of candidate)
     */
    @PostMapping("/updatebankdetails")
    public ResponseEntity<Response> updateBankDetails(@Valid @RequestBody CandidateBankDetails candidateBankDetailsDto) {
        CandidateBankDetails updateDetails = fellowshipCandidateService.updateBankDetails(candidateBankDetailsDto);
        return new ResponseEntity<>(new Response(updateDetails, 200, ApplicationConfig.getMessageAccessor().getMessage("110")), HttpStatus.OK);
    }

    /**
     * @param candidateQualificationDto
     * @return response(Updated qualification details of candidate)
     */
    @PostMapping("/updatequalificationdetails")
    public ResponseEntity<Response> updateQualificationDetails(@Valid @RequestBody CandidateQualificationDTO candidateQualificationDto) {
        CandidateQualification updateDetails = fellowshipCandidateService.updateQualificationDetails(candidateQualificationDto);
        return new ResponseEntity<>(new Response(updateDetails, 200, ApplicationConfig.getMessageAccessor().getMessage("110")), HttpStatus.OK);
    }

    /**
     * @param file
     * @return response(Url of data)
     */
    @PostMapping("/upload")
    public String uploadDocuments(@RequestParam("file") MultipartFile file, @RequestParam(value = "id") String id) {
        String url = fellowshipCandidateService.uploadDocuments(file, id);
        return "File uploaded successfully: File path :  " + url;
    }

}
