package com.bl.lms.service;

import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.Candidate;
import com.bl.lms.model.FellowshipCandidate;
import com.bl.lms.repository.CandidateRepository;
import com.bl.lms.repository.FellowshipCandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class FellowshipCandidateServiceImpl implements IFellowshipCandidateService{

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    @Autowired
    private CandidateRepository hiredCandidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender sender;

    @Override
    public Response joinCandidateToFellowship(FellowshipCandidateDTO fellowshipCandidateDTO) {
        Candidate acceptedCandidate = hiredCandidateRepository.findByStatusAndId("Accepted", fellowshipCandidateDTO.getId());
        fellowshipCandidateDTO.setIsBirthDateVerified("yes");
        fellowshipCandidateDTO.setPhotoPath("verified");
        fellowshipCandidateDTO.setJoiningDate(LocalDate.now());
        fellowshipCandidateDTO.setDocumentStatus("Pending");
        fellowshipCandidateDTO.setRemark("OK");
        modelMapper.map(acceptedCandidate, fellowshipCandidateDTO);
        FellowshipCandidate fellowshipCandidate = modelMapper.map(fellowshipCandidateDTO, FellowshipCandidate.class);
        fellowshipCandidateRepository.save(fellowshipCandidate);
        return new Response(200, "Candidate Joined Successfully");
    }

    @Override
    public Response getCandidateCount() {
        long candidateCount = fellowshipCandidateRepository.count();
        return new Response(200, "Total no of candidates: " + candidateCount);
    }
}
