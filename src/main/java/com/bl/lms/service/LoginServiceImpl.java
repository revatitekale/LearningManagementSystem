package com.bl.lms.service;

import com.bl.lms.dto.LoginResponse;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.model.User;
import com.bl.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LoginResponse save(UserDTO user) {
        user.setCreator_stamp(LocalDateTime.now());
        user.setCreator_user(user.getFirst_name());
        user.setVerified("yes");
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        User newUser = modelMapper.map(user, User.class);
        userRepository.save(newUser);
        return new LoginResponse(200, "Register successfull");
    }
}