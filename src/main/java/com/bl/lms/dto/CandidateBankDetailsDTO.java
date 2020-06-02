package com.bl.lms.dto;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CandidateBankDetailsDTO {
    @NotNull
    private long id;
    @NotNull
    private long candidateId;
    @NotNull
    private String name;
    @NotNull
    private long accountNumber;
    @NotNull
    private String isAccountNumberVerified;
    @NotNull
    private String ifscCode;
    @NotNull
    private String isIfscCodeVerified;
    @NotNull
    private String panNumber;
    @NotNull
    private String isPanNumberVerified;
    @NotNull
    private long addhaarNumber;
    @NotNull
    private String isAddhaarNumVerified;
    private LocalDateTime creatorStamp;
    private long creatorUser;
}
