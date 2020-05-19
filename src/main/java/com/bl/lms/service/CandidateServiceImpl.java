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
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    ModelMapper modelMapper;

    CandidateDTO candidateDTO = new CandidateDTO();

    @Override
    public List getHiredCandidate(String filePath) throws IOException {

        List sheetData = new ArrayList();

        try (FileInputStream fis = new FileInputStream(filePath)) {

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
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

    @Override
    public void saveCandidateDetails(List sheetData) {
        XSSFCell cell;
        for (int i = 1; i < sheetData.size(); i++) {
            int j = 0;
            List list = (List) sheetData.get(i);
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setId((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setFirst_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setMiddle_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setLast_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setEmail(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setHired_city(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setDegree(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setHired_date(cell.getDateCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setMobile_number((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setPermanent_pincode((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setHired_lab(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setAttitude(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setCommunication_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setKnowledge_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setAggregate_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setStatus(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setCreator_stamp(cell.getDateCellValue());
            cell = (XSSFCell) list.get(j++);
            candidateDTO.setCreator_user(cell.getStringCellValue());
            Candidate candidateModel = modelMapper.map(candidateDTO, Candidate.class);
            candidateRepository.save(candidateModel);
        }
    }

    @Override
    public List getHiredCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate findByFirst_name(String name) {
        Candidate hiredCandidateModel = candidateRepository.findByFirst_name(name);
        return hiredCandidateModel;
    }
}

