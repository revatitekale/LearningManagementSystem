package com.bl.lms.model;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "candidate_bank_details")
public class CandidateBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long candidateId;
    private String name;
    private long accountNumber;
    private String isAccountNumberVerified;
    private String ifscCode;
    private String isIfscCodeVerified;
    private String panNumber;
    private String isPanNumberVerified;
    private long addhaarNumber;
    private String isAddhaarNumVerified;
    private LocalDateTime creatorStamp;
    private long creatorUser;

    public LocalDateTime getCreatorStamp() {
        return creatorStamp;
    }

    public void setCreatorStamp(LocalDateTime creatorStamp) {
        this.creatorStamp = LocalDateTime.now();
    }

    public long getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(long creatorUser) {
        this.creatorUser = this.candidateId;
    }
}
