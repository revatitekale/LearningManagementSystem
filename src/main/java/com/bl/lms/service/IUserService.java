package com.bl.lms.service;

import com.bl.lms.dto.LoginDTO;
import com.bl.lms.dto.Response;
import com.bl.lms.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface IUserService {
        Response register(UserDTO user);

        Response sentEmail(String email) throws MessagingException;
        boolean resetPassword(String password, String token);
        String getAuthenticationToken(LoginDTO authenticationRequest) throws Exception;
}
