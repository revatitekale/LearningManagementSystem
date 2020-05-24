package com.bl.lms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private long contact_number;
    private String Verified;
    private LocalDateTime creator_stamp;
    private String creator_user;

}
