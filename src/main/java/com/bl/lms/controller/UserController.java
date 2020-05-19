package com.bl.lms.controller;

import com.bl.lms.dto.*;
import com.bl.lms.model.User;
import com.bl.lms.repository.UserRepository;
import com.bl.lms.service.UserServiceImpl;
import com.bl.lms.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtToken jwtTokenUtil;

    @Autowired
    UserServiceImpl jwtUserServiceImpl;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(jwtUserServiceImpl.register(user));
    }

    @RequestMapping({"/login"})
    public String login() {
        return "Login successFull";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserServiceImpl
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new ResponseJwt(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/forgetpassword")
    public Response requestResetPassword(@RequestBody ForgetPasswordDTO passwordRequestModel) throws MessagingException {
        User user = userRepository.findByEmail(passwordRequestModel.getEmail());
        final String token = jwtTokenUtil.generatePasswordResetToken(String.valueOf(user.getId()));
        System.out.println("token");
        jwtUserServiceImpl.sentEmail(user, token);
        return new Response(200, "Email sent successfully");
    }

    @PutMapping("/resetpassword")
    public Response resetPassword(@RequestBody ForgetPasswordDTO resetPassword) {
        boolean result = jwtUserServiceImpl.resetPassword(resetPassword.getPassword(), resetPassword.getToken());
        if (result)
            return new Response(200, "Successfully updated");
        return null;
    }
}
