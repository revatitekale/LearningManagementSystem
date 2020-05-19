package com.bl.lms.dto;


import javax.validation.constraints.Pattern;

public class ForgetPasswordDTO {

    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*.{8,}$")
    private String password;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
