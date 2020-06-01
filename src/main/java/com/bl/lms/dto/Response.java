package com.bl.lms.dto;
import lombok.Data;

public class Response {
    public Object data;
    public int status;
    public String message;

    public Response(Object data, int status, String message) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
