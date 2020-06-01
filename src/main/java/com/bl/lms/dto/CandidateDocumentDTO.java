package com.bl.lms.dto;

import com.bl.lms.model.FellowshipCandidate;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class CandidateDocumentDTO {
    @NotNull
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
    @Builder.Default
    private String status;
    @NotNull
    @Builder.Default
    private LocalDateTime creatorStamp = LocalDateTime.now();
    @NotNull
    private String creatorUser;
}
