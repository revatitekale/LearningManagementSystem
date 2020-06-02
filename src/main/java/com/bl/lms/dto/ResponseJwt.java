package com.bl.lms.dto;

import lombok.Data;


@Data
public class ResponseJwt {

    public String jwttoken;
    public String message;

    public ResponseJwt(String token, String message) {
        this.jwttoken = token;
        this.message = message;
    }

}
