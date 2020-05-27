package com.bl.lms.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class FellowshipCandidateDTO {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String degree;
    private String hiredCity;
    private Date hiredDate;
    private long mobileNumber;
    private long permanentPincode;
    private String hiredLab;
    private String attitude;
    private String communicationRemark;
    private String knowledgeRemark;
    private String aggregateRemark;
    private String status;
    private Date creatorStamp;
    private String creatorUser;
    private Date birthDate;
    private String isBirthDateVerified;
    private String parentOccupation;
    private long parentMobileNumber;
    private double parentAnnualSalary;
    private String localAddress;
    private String permanentAddress;
    private String photoPath;
    private LocalDate joiningDate;
    private String candidateStatus;
    private String documentStatus;
    private String remark;
}
