package com.bl.lms.service;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.Candidate;
import com.bl.lms.model.CandidateBankDetails;
import com.bl.lms.model.CandidateQualification;
import com.bl.lms.model.FellowshipCandidate;
import com.bl.lms.repository.BankDetailsRepository;
import com.bl.lms.repository.CandidateRepository;
import com.bl.lms.repository.FellowshipCandidateRepository;
import com.bl.lms.repository.QualificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class FellowshipCandidateServiceImpl implements IFellowshipCandidateService{

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    @Autowired
    private CandidateRepository hiredCandidateRepository;

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

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

    @Override
    public Response updateCandidateBankInfo(CandidateBankDetailsDTO candidateBankDetailsDTO) {
        candidateBankDetailsDTO.setIsAccountNumberVerified("Yes");
        candidateBankDetailsDTO.setIsAdhaarNumVerified("Yes");
        candidateBankDetailsDTO.setIsIfscCodeVerified("Yes");
        candidateBankDetailsDTO.setIsPanNumberVerified("Yes");
        candidateBankDetailsDTO.setCreatorStamp(LocalDateTime.now());
        candidateBankDetailsDTO.setCreatorUser(candidateBankDetailsDTO.getName());
        CandidateBankDetails candidateBankDetails = modelMapper.map(candidateBankDetailsDTO, CandidateBankDetails.class);
        bankDetailsRepository.save(candidateBankDetails);
        return new Response(200, "Bank Details Successfully Updated");
    }

    @Override
    public Response updateQualificationDetails(CandidateQualificationDTO candidateQualificationDTO) {
        candidateQualificationDTO.setIsDegreeNameVerified("Yes");
        candidateQualificationDTO.setIsEmployeeDisciplinedVerified("Yes");
        candidateQualificationDTO.setIsFinalYearPercentageVerified("Yes");
        candidateQualificationDTO.setIsOtherTrainingVerified("Yes");
        candidateQualificationDTO.setIsTrainingInstituteVerified("Yes");
        candidateQualificationDTO.setIsPassingYearVerified("Yes");
        candidateQualificationDTO.setIsTrainingDurationMonthVerified("Yes");
        candidateQualificationDTO.setCreatorStamp(LocalDateTime.now());
        candidateQualificationDTO.setCreatorUser("Admin");
        CandidateQualification candidateQualification = modelMapper.map(candidateQualificationDTO,CandidateQualification.class);
        qualificationRepository.save(candidateQualification);
        return new Response(200, "Candidate Qualification Details Successfully Updated");
    }
}
