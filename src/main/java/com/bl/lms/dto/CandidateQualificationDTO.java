package com.bl.lms.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CandidateQualificationDTO {

    @NotNull
    private long id;
    @NotNull
    private long candidateId;
    @NotNull
    private String diploma;
    @NotNull
    private String degreeName;
    private String isDegreeNameVerified;
    @NotNull
    private String employeeDescipline;
    private String isEmployeeDisciplinedVerified;
    @NotNull
    private long passingYear;
    private String isPassingYearVerified;
    @NotNull
    private double aggregatePercentage;
    @NotNull
    private double finalYearPercentage;
    private String isFinalYearPercentageVerified;
    private String trainingInstitute;
    private String isTrainingInstituteVerified;
    private long trainingDurationMonth;
    private String isTrainingDurationMonthVerified;
    private String otherTraining;
    private String isOtherTrainingVerified;
    private LocalDateTime creatorStamp;
    private long creatorUser;
}
