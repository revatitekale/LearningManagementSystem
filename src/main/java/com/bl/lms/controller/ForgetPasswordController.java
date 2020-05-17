package com.bl.lms.controller;

import com.bl.lms.dto.ForgetPassword;
import com.bl.lms.dto.LoginResponse;
import com.bl.lms.dto.ResetPassword;
import com.bl.lms.model.User;
import com.bl.lms.repository.UserRepository;
import com.bl.lms.service.ForgetPasswordService;
import com.bl.lms.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.AddressException;

@RestController
public class ForgetPasswordController {

    @Autowired
    JwtToken jwtToken;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ForgetPasswordService forgetPasswordService;

    @PostMapping("/request_reset_password")
    public LoginResponse requestResetPassword(@RequestBody ForgetPassword passwordRequestModel) throws AddressException, MessagingException {
        User user = userRepository.findByEmail(passwordRequestModel.getEmail());
        final String token = jwtToken.generatePasswordResetToken(String.valueOf(user.getId()));
        forgetPasswordService.sentEmail(user, token);
        return new LoginResponse(200, "Email sent successfully");
    }

    @PutMapping("/reset_password")
    public LoginResponse resetPassword(@RequestBody ResetPassword passwordRequestModel) {
        boolean result = forgetPasswordService.resetPassword(passwordRequestModel.getPassword(), passwordRequestModel.getToken());
        if (result)
            return new LoginResponse(200, "Successfully updated");
        return null;
    }
}
