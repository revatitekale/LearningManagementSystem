package com.bl.lms.service;

import com.bl.lms.dto.CandidateBankDetailsDTO;
import com.bl.lms.dto.CandidateQualificationDTO;
import com.bl.lms.dto.FellowshipCandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


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
    public FellowshipCandidate joinCandidateToFellowship(long id) {
        Candidate hiredCandidateModel = hiredCandidateRepository.findById(id)
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.INVALID_ID, "User not found"));
        FellowshipCandidate fellowshipCandidateModel = modelMapper.map(hiredCandidateModel, FellowshipCandidate.class);
        if (fellowshipCandidateModel.equals(null))
            throw new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Null Value");
        return fellowshipCandidateRepository.save(fellowshipCandidateModel);
    }

    @Override
    public int getCandidateCount() {
        List<FellowshipCandidate> list = fellowshipCandidateRepository.findAll();
        return list.size();
    }

    @Override
    public FellowshipCandidate updateInformation(FellowshipCandidateDTO fellowshipCandidateDto) {
        Candidate hiredCandidateModel = hiredCandidateRepository.findById(fellowshipCandidateDto.getId())
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType
                        .INVALID_ID, "User not found"));
        modelMapper.map(hiredCandidateModel,fellowshipCandidateDto);
        FellowshipCandidate fellowshipMappedCandidate = modelMapper.map(fellowshipCandidateDto, FellowshipCandidate.class);
        return fellowshipCandidateRepository.save(fellowshipMappedCandidate);
    }

    @Override
    public void sendMail(FellowshipCandidate fellowshipCandidateModel) throws MessagingException {
        String recipientAddress = fellowshipCandidateModel.getEmail();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipientAddress);
        helper.setText("Hii, " + fellowshipCandidateModel.getFirstName() + " " + fellowshipCandidateModel.getLastName() + " " +
                "You have been selected for Fellowship Program." + "\n" + "join: " +
                "\n" + "http://localhost:8084/candidatehiring" +
                "/status?response=Accepted&email=" + fellowshipCandidateModel.getEmail() + "\n\n"
                + "Reject: " + "\n" + "http://localhost:8084/" +
                "candidatehiring/status?response=Rejected&email=" + fellowshipCandidateModel.getEmail() + "\n\n");
        helper.setSubject("Job offer notification");
        //sender.send(message);
    }

    @Override
    public CandidateBankDetails updateBankDetails(CandidateBankDetails bankDetailsDto) {
        fellowshipCandidateRepository.findById(bankDetailsDto.getCandidateId())
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.INVALID_ID, "Invalid Id"));
        CandidateBankDetails bankDetailsModel = modelMapper.map(bankDetailsDto, CandidateBankDetails.class);
        bankDetailsModel.setCreatorStamp(new LocalDateTime());
        bankDetailsModel.setCreatorUser(bankDetailsDto.getName());
        return bankDetailsRepository.save(bankDetailsModel);
    }

    @Override
    public CandidateQualification updateQualificationDetails(CandidateQualificationDTO candidateQualificationDto) {
        fellowshipCandidateRepository.findById(candidateQualificationDto.getCandidateId())
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.INVALID_ID, "Invalid id"));
        CandidateQualification qualificationDetails = modelMapper.map(candidateQualificationDto, CandidateQualification.class);
        qualificationDetails.setCreatorStamp(new Date());
        return qualificationRepository.save(qualificationDetails);
    }
}
