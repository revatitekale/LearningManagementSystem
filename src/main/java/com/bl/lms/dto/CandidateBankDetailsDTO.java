package com.bl.lms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CandidateBankDetailsDTO {
    @NotNull
    private long id;
    @NotNull
    private long candidateId;
    private String name;
    private long accountNumber;
    private String isAccountNumberVerified;
    private String ifscCode;
    private String isIfscCodeVerified;
    private String panNumber;
    private String isPanNumberVerified;
    private long addhaarNumber;
    private String isAdhaarNumVerified;
    private LocalDateTime creatorStamp;
    private String creatorUser;
}
