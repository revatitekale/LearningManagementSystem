package com.bl.lms.service;

import com.bl.lms.dto.CandidateDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.Candidate;
import com.bl.lms.repository.CandidateRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements ICandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ModelMapper modelMapper;

    CandidateDTO candidateDTO = new CandidateDTO();

    /**
     *
     * @param filePath
     * @return Method to read excel file and store data to database
     * @throws IOException
     */
    @Override
    public boolean getHiredCandidate(MultipartFile filePath) throws IOException {
        boolean flag = true;
        CandidateDTO hiredCandidateDto = new CandidateDTO();
        try (InputStream fis = filePath.getInputStream()) {
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator rows = sheet.rowIterator();
            XSSFCell cell;
            //For each row, iterate through all the columns
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                if (!flag) {
                    while (cells.hasNext()) {
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setId((long) cell.getNumericCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setFirstName(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setMiddleName(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setLastName(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setEmail(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setHiredCity(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setDegree(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setHiredDate(cell.getDateCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setMobileNumber((long) cell.getNumericCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setPermanentPincode((long) cell.getNumericCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setHiredLab(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setAttitude(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setCommunicationRemark(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setKnowledgeRemark(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setAggregateRemark(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setStatus(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setCreatorStamp(LocalDateTime.now());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDto.setCreatorUser(hiredCandidateDto.getId());
                        saveCandidateDetails(hiredCandidateDto);
                        sendEmail(hiredCandidateDto);
                    }
                }
                flag = false;
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * @param candidateDTO
     * @throws MessagingException
     * sent email to each candidate.
     */
    @Override
    public void sendEmail(CandidateDTO candidateDTO) throws MessagingException {
        String recipientAddress = candidateDTO.getEmail();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipientAddress);
        helper.setText("Hii, " + candidateDTO.getFirstName() + " " + candidateDTO.getLastName() + " " +
                "You have been selected for Fellowship Program." + "\n" + "join: " +
                "\n" + "http://localhost:8084/candidatehiring" +
                "/status?response=Accepted&email=" + candidateDTO.getEmail() + "\n\n"
                + "Reject: " + "\n" + "http://localhost:8084/" +
                "candidatehiring/status?response=Rejected&email=" + candidateDTO.getEmail() + "\n\n");
        helper.setSubject("Job offer notification");
    }

    /**
     * @param candidateDTO
     * @return save candidate details to database.
     */
    @Override
    public void saveCandidateDetails(CandidateDTO candidateDTO) throws MessagingException {
        Candidate hiredCandidate = modelMapper.map(candidateDTO, Candidate.class);
        if (hiredCandidate == null)
            throw new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Data not found");
        candidateRepository.save(hiredCandidate);
    }

    /**
     * @return list of hired candidate.
     */
    @Override
    public List getHiredCandidatesList() {
        return candidateRepository.findAll();
    }

    /**
     * @param id
     * @return candidate details.
     */
    @Override
    public Optional<Candidate> showById(Long id) {
        return candidateRepository.findById(id);
    }

    /**
     * @param email
     * @param status
     * @return Updated candidate status.
     */
    @Override
    public Candidate updateCandidateStatus(String email, String status) throws LmsAppException {
        return candidateRepository.findByEmail(email)
                .map(hiredCandidateModel -> {
                    hiredCandidateModel.setStatus(status);
                    return candidateRepository.save(hiredCandidateModel);
                }).orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, "Data not found"));
    }
}