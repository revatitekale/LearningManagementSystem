package com.bl.lms.dto;
import lombok.Data;

@Data

public class Response {
    int status;
    String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
