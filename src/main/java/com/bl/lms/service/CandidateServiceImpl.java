package com.bl.lms.service;

import com.bl.lms.dto.CandidateDTO;
import com.bl.lms.dto.Response;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CandidateServiceImpl implements ICandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ModelMapper modelMapper;

    CandidateDTO candidateDTO = new CandidateDTO();

    //READ DATA FROM EXCELSHEET AND STORE INTO DATABASE
    @Override
    public Response getHiredCandidate(MultipartFile filePath) throws IOException {
        CandidateDTO hiredCandidateDTO = new CandidateDTO();
        boolean flag = true;
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
                        hiredCandidateDTO.setId((long) cell.getNumericCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setFirstName(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setMiddleName(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setLastName(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setEmail(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setHiredCity(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setDegree(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setHiredDate(cell.getDateCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setMobileNumber((long) cell.getNumericCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setPermanentPincode((int) cell.getNumericCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setHiredLab(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setAttitude(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setCommunicationRemark(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setKnowledgeRemark(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setAggregateRemark(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setStatus(cell.getStringCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setCreatorStamp(cell.getDateCellValue());
                        cell = (XSSFCell) cells.next();
                        hiredCandidateDTO.setCreatorUser(cell.getStringCellValue());
                        saveCandidateDetails(hiredCandidateDTO);
                    }
                }
                flag = false;
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return new Response(200, "Data Successfully Added");
    }

    //SAVE HIRE CANDIDATE DETAILS
    @Override
    public void saveCandidateDetails(CandidateDTO sheetData) throws MessagingException {
        Candidate candidate = modelMapper.map(sheetData, Candidate.class);
        candidateRepository.save(candidate);
        this.sendMail(candidate);
    }

    //GET THE LIST OF CANDIDATE
    @Override
    public List getHiredCandidatesList() {
        return candidateRepository.findAll();
    }

    //FIND CANDIDATE BY FIRST NAME
    @Override
    public Candidate showById(Long id) {
        Candidate candidateModel = candidateRepository.findById(id).get();
        return candidateModel;
    }

    //SEND MAIL TO SELECTED CANDIDATE
    @Override
    public void sendMail(Candidate hiredCandidate) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(hiredCandidate.getEmail());
        helper.setText("Hii, " + hiredCandidate.getFirstName() + " " + hiredCandidate.getLastName() + " " +
                "You have been selected to our Fellowship Program. please click on the following " +
                 "\n" + "http://localhost:8080/candidatehiring" +
                "/status?response=Accepted&email=" + hiredCandidate.getEmail() + "\n\n"
                + "Please click on following link to reject the offer. " + "\n" + "http://localhost:8080/" +
                "candidatehiring/status?response=Rejected&email=" + hiredCandidate.getEmail() + "\n\n");
        helper.setSubject("Fellowship Offer From Bridgelabz");
        javaMailSender.send(message);
    }

    //UPDATE STATUS OF CANDIDATE
    @Override
    public Response updateStatus(String response, String email) {
        Candidate candidate = candidateRepository.findByEmail(email);
        candidate.setStatus(response);
        candidateRepository.save(candidate);
        return new Response(200, "Status Updated Successfully");
    }

    //SEND JOB OFFER TO HIRED CANDIDATE
    @Override
    public Response sendJobOffer() throws MessagingException {
        List<Candidate> acceptedCandidates = candidateRepository.findByStatus("Accepted");
        for (Candidate candidate : acceptedCandidates) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(candidate.getEmail());
            helper.setText("Hii, " + candidate.getFirstName() + " " + candidate.getLastName() + " " +
                    "As per your confirmation, You have been officially selected to BridgeLabz Fellowship" +
                    " Program." + "\n\n" + "We your personal information, " +
                    "your bank information and your educational information " + "\n\n"
                    + "Click on following links to do the same." +
                    "\n\n" +
                    "http://localhost:8080/fellowshipdetails/updatedetails" + "\n\n" +
                    "http://localhost:8080/qualificationdetails/updatequalificationdetails" + "\n\n" +
                    "http://localhost:8080/bankdetails/updatebankdetails"
            );
            helper.setSubject("Fellowship from BridgeLabz");
            javaMailSender.send(message);
        }
        return new Response(200, "Mail Sent Successfully");
    }
}