package com.bl.lms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long contactNumber;
    private String Verified;
    private LocalDateTime creatorStamp;
    private String creatorUser;

}
