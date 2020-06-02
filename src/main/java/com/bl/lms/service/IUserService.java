package com.bl.lms.service;

import com.bl.lms.dto.LoginDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface IUserService {

        User register(UserDTO user);
        String getPasswordToken(String email) throws MessagingException, LmsAppException;
        User resetPassword(String password, String token);
        String getAuthenticationToken(LoginDTO authenticationRequest) throws Exception;
}
