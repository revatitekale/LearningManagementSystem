package com.bl.lms.service;

import com.bl.lms.dto.Response;
import com.bl.lms.model.CandidateDocuments;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICandidateDocumentService {
    Response uploadFile(MultipartFile file, long id, String type) throws IOException;
    CandidateDocuments getFile(long id);
}
