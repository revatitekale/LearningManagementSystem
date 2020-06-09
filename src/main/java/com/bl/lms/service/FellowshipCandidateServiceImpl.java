package com.bl.lms.service;

import com.bl.lms.configuration.CloudinaryConfiguration;
import com.bl.lms.dto.*;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.*;
import com.bl.lms.repository.*;
import com.bl.lms.util.RabbitMq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private RabbitMq rabbitMq;

    @Autowired
    private EmailDTO mailDto;


    @Autowired
    private CloudinaryConfiguration cloudinaryConfiguration;

    @Autowired
    private CandidateDocumentRepository candidateDocumentRepository;

    /**
     * @param id
     * @return response(fellowshipCandidateModel)
     */
    @Override
    public FellowshipCandidate joinCandidateToFellowship(long id) throws MessagingException {
        Candidate hiredCandidateModel = hiredCandidateRepository.findById(id)
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.INVALID_ID, "User not found"));
        FellowshipCandidate fellowshipCandidateModel = modelMapper.map(hiredCandidateModel, FellowshipCandidate.class);
        sendMail(hiredCandidateModel);
        return fellowshipCandidateRepository.save(fellowshipCandidateModel);
    }

    /**
     * @return Candidate count
     */
    @Override
    public int getCandidateCount() {
        List<FellowshipCandidate> list = fellowshipCandidateRepository.findAll();
        return list.size();
    }

    /**
     * @param fellowshipCandidateDto
     * @return Updated candidates personal information
     */
    @Override
    public FellowshipCandidate updateInformation(FellowshipCandidateDTO fellowshipCandidateDto) {
        Candidate hiredCandidateModel = hiredCandidateRepository.findById(fellowshipCandidateDto.getId())
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType
                        .INVALID_ID, "User not found"));
        modelMapper.map(hiredCandidateModel,fellowshipCandidateDto);
        FellowshipCandidate fellowshipMappedCandidate = modelMapper.map(fellowshipCandidateDto, FellowshipCandidate.class);
        return fellowshipCandidateRepository.save(fellowshipMappedCandidate);
    }

    /**
     * @param bankDetailsDto
     * @return response(Updated bank details)
     */
    @Override
    public CandidateBankDetails updateBankDetails(CandidateBankDetails bankDetailsDto) {
        fellowshipCandidateRepository.findById(bankDetailsDto.getCandidateId())
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.INVALID_ID, "Invalid Id"));
        CandidateBankDetails bankDetailsModel = modelMapper.map(bankDetailsDto, CandidateBankDetails.class);
        return bankDetailsRepository.save(bankDetailsModel);
    }

    /**
     * @param candidateQualificationDto
     * @return Updated qualification details
     */
    @Override
    public CandidateQualification updateQualificationDetails(CandidateQualificationDTO candidateQualificationDto) {
        fellowshipCandidateRepository.findById(candidateQualificationDto.getCandidateId())
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.INVALID_ID, "Invalid id"));
        CandidateQualification qualificationDetails = modelMapper.map(candidateQualificationDto, CandidateQualification.class);
        return qualificationRepository.save(qualificationDetails);
    }

    /**
     * @param file
     * @param updateDocumentDto
     */
    @Override
    public String uploadDocuments(MultipartFile file, String updateDocumentDto) {
        try {
            CandidateDocumentDTO uploadDocumentsDto = new ObjectMapper().readValue(updateDocumentDto, CandidateDocumentDTO.class);
            fellowshipCandidateRepository.findById(uploadDocumentsDto.getId())
                    .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Data not found"));
            if (file.isEmpty())
                throw new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Failed to store empty file");
            Map<Object, Object> parameters = new HashMap<>();
            parameters.put("public_id", "CandidateDocuments/" + uploadDocumentsDto.getId() + "/" + file.getOriginalFilename());
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfiguration.cloudinaryConfig().uploader().upload(uploadedFile, parameters);
            String url = uploadResult.get("url").toString();
            uploadDocumentsDto.setDocumentPath(url);
            CandidateDocuments uploadDocumentsDao = modelMapper.map(uploadDocumentsDto, CandidateDocuments.class);
            candidateDocumentRepository.save(uploadDocumentsDao);
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param file
     * @param id
     * @throws LmsAppException
     * @throws IOException
     */
    @Override
    public boolean upload(MultipartFile file, long id) throws LmsAppException, IOException {
        fellowshipCandidateRepository.findById(id)
                .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        if (file.isEmpty())
            throw new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Files are empty");
        File convertFile = new File(id + "-" + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return false;
    }

    /**
     * @param file
     * @throws IOException
     */
    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

    /**
     * @param hiredCandidate
     * @throws MessagingException
     * response(Sent email to candidates)
     */
    @Override
    public void sendMail(Candidate hiredCandidate) throws MessagingException {
        if (hiredCandidate.getStatus().matches(accept))
            hiredCandidate = hiredCandidateRepository.findByEmail(hiredCandidate.getEmail())
                    .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.USER_NOT_FOUND, "User not found"));
            mailDto.setTo(hiredCandidate.getEmail());
            mailDto.setBody("Hii, " + hiredCandidate.getFirstName() + " " + hiredCandidate.getLastName() + " Congratulations...!!" +
                    "You have been selected for 16 week Fellowship Program of Bridgelabz" + "\n\n");
            mailDto.setFrom("revitekale1910@gmail.com");
            mailDto.setSubject("Fellowship Job Offer");
            rabbitMq.sendMail(mailDto);
        }
}
