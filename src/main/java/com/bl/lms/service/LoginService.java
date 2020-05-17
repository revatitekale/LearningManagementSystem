package com.bl.lms.service;

import com.bl.lms.dto.LoginResponse;
import com.bl.lms.dto.UserDTO;

public interface LoginService {
    public LoginResponse save(UserDTO user);
}
