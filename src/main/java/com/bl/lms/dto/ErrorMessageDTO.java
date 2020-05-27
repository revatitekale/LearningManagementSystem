package com.bl.lms.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO {
    private Date timestamp;
    private String message;
    private String details;
}
