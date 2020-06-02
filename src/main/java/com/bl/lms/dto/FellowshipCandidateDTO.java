package com.bl.lms.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class FellowshipCandidateDTO {

    @NotNull
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    @Email
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
    private LocalDateTime creatorStamp;
    private long creatorUser;
    @NotNull
    private Date birthDate;
    private String isBirthDateVerified;
    private String parentOccupation;
    private long parentMobileNumber;
    private double parentAnnualSalary;
    private String localAddress;
    private String permanentAddress;
    private String photoPath;
    private Date joiningDate;
    private String candidateStatus;
    private String documentStatus;
    private String remark;
}
