package com.bl.lms.service;

import com.bl.lms.configuration.ApplicationConfig;
import com.bl.lms.dto.CandidateDocumentDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.CandidateDocuments;
import com.bl.lms.model.FellowshipCandidate;
import com.bl.lms.repository.CandidateDocumentRepository;
import com.bl.lms.repository.FellowshipCandidateRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CandidateDocumentService implements ICandidateDocumentService{

    @Autowired
    private Cloudinary cloudinaryConfig;

    @Autowired
    private CandidateDocumentRepository candidateDocumentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    private String uploadFile(MultipartFile file) throws IOException {
        try {
            fellowshipCandidateRepository.findById(id)
                    .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Data not found"));
            if (file.isEmpty())
                throw new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Failed to store empty file");
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            return  uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File  convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    @Override
    public CandidateDocuments getFile(long id) {
        return candidateDocumentRepository.findById(id).get();
    }

}
