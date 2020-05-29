package com.bl.lms.service;

import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.CandidateQualification;
import com.bl.lms.repository.QualificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CandidateQualificationImpl implements ICandidateQualification{

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private ModelMapper modelMapper;


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
        return new Response(200, "Candidate Qualification Details Updated Successfully");
    }
}
