package com.bl.lms.controller;

import com.bl.lms.dto.*;
import com.bl.lms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseJwt> createAuthenticationToken(@RequestBody LoginDTO loginRequest) throws Exception {
        String token = userService.getAuthenticationToken(loginRequest);
        return ResponseEntity.ok(new ResponseJwt(token));
    }

    @GetMapping("/homepage")
    public String login() {
        return "Welcome to Learner Management System";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userService.register(user));
    }

    @GetMapping("/forgetpassword")
    public Response requestResetPassword(@RequestParam (value = "email") String email) throws MessagingException {
        return userService.sentEmail(email);
    }

    @PutMapping("/resetpassword")
    public Response resetPassword(@RequestBody ForgetPasswordDTO resetPassword) {
        boolean result = userService.resetPassword(resetPassword.getPassword(), resetPassword.getToken());
        if (result)
            return new Response(200, "Successfully updated");
        return null;
    }
}