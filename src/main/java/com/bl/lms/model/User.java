package com.bl.lms.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table
@Entity(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private long contact_number;
    private String Verified;
    private LocalDateTime creator_stamp;
    private String creator_user;

}
