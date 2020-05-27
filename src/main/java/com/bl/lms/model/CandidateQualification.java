package com.bl.lms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table
@Entity(name = "candidate_qualification")
public class CandidateQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long candidateId;
    private String diploma;
    private String degreeName;
    private String isDegreeNameVerified;
    private String employeeDescipline;
    private String isEmployeeDisciplinedVerified;
    private long passingYear;
    private String isPassingYearVerified;
    private double aggregatePercentage;
    private double finalYearPercentage;
    private String isFinalYearPercentageVerified;
    private String trainingInstitute;
    private String isTrainingInstituteVerified;
    private long trainingDurationMonth;
    private String isTrainingDurationMonthVerified;
    private String otherTraining;
    private String isOtherTrainingVerified;
    private Date creatorStamp;
    private String creatorUser;
}
