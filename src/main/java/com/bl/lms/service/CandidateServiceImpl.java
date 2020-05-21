package com.bl.lms.service;

import com.bl.lms.dto.CandidateDTO;
import com.bl.lms.model.Candidate;
import com.bl.lms.repository.CandidateRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CandidateServiceImpl implements ICandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    ModelMapper modelMapper;

    CandidateDTO candidateDTO = new CandidateDTO();

    //READ DATA FROM EXCELSHEET AND STORE INTO DATABASE
    @Override
    public List getHiredCandidate(String filePath) throws IOException {

        List sheetData = new ArrayList();

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                List data = new ArrayList();
                while (cells.hasNext()) {
                    XSSFCell cell = (XSSFCell) cells.next();
                    data.add(cell);
                }
                sheetData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
    }

    //SAVE HIRE CANDIDATE DETAILS
    @Override
    public void saveCandidateDetails(List sheetData) {
        XSSFCell cell;
        for (int row = 1; row < sheetData.size(); row++) {
            int coloumn = 0;
            List list = (List) sheetData.get(row);
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setId((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setFirst_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setMiddle_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setLast_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setEmail(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setHired_city(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setDegree(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setHired_date(cell.getDateCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setMobile_number((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setPermanent_pincode((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setHired_lab(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setAttitude(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setCommunication_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setKnowledge_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setAggregate_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setStatus(cell.getStringCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setCreator_stamp(cell.getDateCellValue());
            cell = (XSSFCell) list.get(coloumn++);
            candidateDTO.setCreator_user(cell.getStringCellValue());
            Candidate candidateModel = modelMapper.map(candidateDTO, Candidate.class);
            candidateRepository.save(candidateModel);
        }
    }

    @Override
    public List getHiredCandidates() {
        return candidateRepository.findAll();
    }

    //FIND CANDIDATE BY FIRST NAME
    @Override
    public Candidate showByFirstName(String name) {
        Candidate candidateModel = candidateRepository.findByFirstName(name);
        return candidateModel;
    }
}

