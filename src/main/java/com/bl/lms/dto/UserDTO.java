package com.bl.lms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    @NotNull
    private long id;
    @Pattern(regexp = "^[A-Z]+[A-Za-z0-9]+$")
    private String firstName;
    @Pattern(regexp = "^[A-Z]+[A-Za-z0-9]+$")
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*.{8,}$")
    private String password;
    private long contactNumber;
    private String verified;
    @NotNull
    private LocalDateTime creatorStamp;
    private long creatorUser;

}
