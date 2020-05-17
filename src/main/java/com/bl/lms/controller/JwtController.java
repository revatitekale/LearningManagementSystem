package com.bl.lms.controller;

import com.bl.lms.dto.RequestJwt;
import com.bl.lms.dto.ResponseJwt;
import com.bl.lms.service.JwtUserService;
import com.bl.lms.util.JwtToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController("/signIn")
@CrossOrigin
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserService jwtUserService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody RequestJwt authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails);

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

}
