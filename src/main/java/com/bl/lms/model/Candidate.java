package com.bl.lms.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table
@Entity(name = "hired_candidate")
public class Candidate implements Serializable {

    @Id
    private long id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String hired_city;
    private String degree;
    private Date hired_date;
    private long mobile_number;
    private long permanent_pincode;
    private String hired_lab;
    private String attitude;
    private String communication_remark;
    private String knowledge_remark;
    private String aggregate_remark;
    private String status;
    private Date creator_stamp;
    private String creator_user;

}
