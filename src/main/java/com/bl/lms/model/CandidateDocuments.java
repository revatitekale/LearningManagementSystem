package com.bl.lms.model;

import org.hibernate.annotations.OnDelete;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CandidateDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @JoinColumn(name = "candidate_id")
    private FellowshipCandidate fellowshipCandidate;
    @NotNull
    private String documentType;
    @NotNull
    private String fileType;
    @NotNull
    private String documentName;
    @NotNull
    private String documentPath;
    @NotNull
    private String status;
    @NotNull
    private LocalDateTime creatorStamp;
    @NotNull
    private String creatorUser;
}
