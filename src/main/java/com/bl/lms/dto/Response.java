package com.bl.lms.dto;
import lombok.Data;

@Data
public class Response {
    public Object status;
    public String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
