package com.bl.lms.service;

import com.bl.lms.dto.Response;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.model.User;

import javax.mail.MessagingException;

public interface UserService {
    Response register(UserDTO user);
    boolean resetPassword(String password, String token);
    void sentEmail(User user, String token) throws MessagingException;
}
