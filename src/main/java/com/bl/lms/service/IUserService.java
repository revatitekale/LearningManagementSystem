package com.bl.lms.service;

import com.bl.lms.dto.Response;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface IUserService {
        Response register(UserDTO user);
        boolean resetPassword(String password, String token);
        void sentEmail(User user, String token) throws MessagingException;
        UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
