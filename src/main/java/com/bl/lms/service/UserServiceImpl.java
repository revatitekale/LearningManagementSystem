package com.bl.lms.service;

import com.bl.lms.dto.LoginDTO;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.User;
import com.bl.lms.repository.UserRepository;
import com.bl.lms.util.JwtToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EntityManager entityManager;

    /**
     * @param user
     * @return response(Method to register user)
     */
    @Override
    public User register(UserDTO user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        User newUser = modelMapper.map(user, User.class);
        User registeredUser = userRepository.save(newUser);
        registeredUser.setCreatorUser(registeredUser.getId());
        return userRepository.save(newUser);
    }

    /**
     * @param email
     * @return Method to get password reset token.
     * @throws MessagingException
     */
    @Override
    public String getPasswordToken(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        final String token = jwtToken.generatePasswordResetToken(String.valueOf(user.getId()));
        sentEmail(user, token);
        return token;
    }

    /**
     * @param user, token
     * @return Method to send reset password request email.
     * @throws MessagingException
     */
    public void sentEmail(User user, String token) throws MessagingException {
        String recipientAddress = user.getEmail();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipientAddress);
        helper.setText("Hii " + user.getFirstName() + "\n" + " You requested to reset password, if YES then click on link put your new password and NO then ignore \n" +
                "http://localhost:8084/forgetpassword?json={%22password%22:%22" + null + "%22+,%22token%22:" + token + "}");
        helper.setSubject("Request to Reset Password");
        sender.send(message);
    }

    /**
     * @param authenticationRequest
     * @return Method to get authentication token
     * @throws Exception
     */
    @Override
    public String getAuthenticationToken(LoginDTO authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtToken.generateToken(userDetails);
        return token;
    }

    /**
     * @param username, password
     * @return Method to authenticate user.
     * @throws Exception
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * @param username
     * @return response(Method to load user by its username)
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByFirstName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(),
                new ArrayList<>());
    }

    /**
     * @param password
     * @param token
     * @return Method to reset password.
     */
    @Override
    public User resetPassword(String password, String token) {
        String encodedPassword = bcryptEncoder.encode(password);
        if (jwtToken.isTokenExpired(token)) {
            throw new LmsAppException(LmsAppException.exceptionType.INVALID_TOKEN, "Token expired");
        }
        long id = Long.valueOf(jwtToken.getUsernameFromToken(token));
        return userRepository.findById(id)
                .map(user -> {
                    user.setPassword(encodedPassword);
                    return user;
                })
                .map(userRepository::save).get();
    }
}
