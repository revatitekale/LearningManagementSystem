package com.bl.lms.service;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.model.CandidateBankDetails;
import com.bl.lms.repository.BankDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CandidateBankDetailsImpl implements ICandidateBankDetails{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BankDetailsRepository bankDetailsRepository;


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
}
