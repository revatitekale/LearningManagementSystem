package com.bl.lms.controller;

import com.bl.lms.configuration.ApplicationConfig;
import com.bl.lms.dto.*;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.User;
import com.bl.lms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * @param userDTO
     * @return response(Registered user)
     */
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserDTO userDTO) {
        User userModel = userService.register(userDTO);
        return new ResponseEntity<>(new Response(userModel, 200, ApplicationConfig.getMessageAccessor().getMessage("101")), HttpStatus.CREATED);

    }

    /**
     * @param loginRequest
     * @return response(token)
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseJwt> createAuthenticationToken(@RequestBody LoginDTO loginRequest) throws Exception {
        String token = userService.getAuthenticationToken(loginRequest);
        return new ResponseEntity<>(new ResponseJwt(token, ApplicationConfig.getMessageAccessor().getMessage("106")), HttpStatus.CREATED);

    }

    /**
     * @param email
     * @return passwordToken
     * @throws MessagingException
     * @throws LmsAppException
     */
    @GetMapping("/forgetpassword")
    public ResponseEntity<Response> requestResetPassword(@Valid @RequestParam (value = "email") String email) throws MessagingException, LmsAppException {
        String passwordToken = userService.getPasswordToken(email);
        return new ResponseEntity<>(new Response(passwordToken, 200, ApplicationConfig.getMessageAccessor().getMessage("103")), HttpStatus.ACCEPTED);
    }

    /**
     * @param resetPassword
     * @return response(Reseted password)
     */
    @PutMapping("/resetpassword")
    public ResponseEntity<Response> resetPassword(@Valid @RequestBody ForgetPasswordDTO resetPassword) {
        User user = userService.resetPassword(resetPassword.getPassword(), resetPassword.getToken());
        if (!user.equals(null))
            return new ResponseEntity<>(new Response(user, 200, ApplicationConfig.getMessageAccessor().getMessage("104")), HttpStatus.ACCEPTED);
        return null;
    }
}