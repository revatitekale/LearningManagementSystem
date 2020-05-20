package com.bl.lms.service;

import com.bl.lms.dto.Response;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.model.User;
import com.bl.lms.repository.UserRepository;
import com.bl.lms.util.JwtToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Component("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByFirstName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getFirst_name(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public Response register(UserDTO user) {
        user.setCreator_stamp(LocalDateTime.now());
        user.setCreator_user(user.getFirst_name());
        user.setVerified("yes");
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        User newUser = modelMapper.map(user, User.class);
        userRepository.save(newUser);
        return new Response(200, "Register successfull");
    }

    @Override
    public boolean resetPassword(String password, String token) {

        String encodedPassword = bcryptEncoder.encode(password);
        if (jwtToken.isTokenExpired(token)) {
            return false;
        }
        long id = Long.parseLong(jwtToken.getUsernameFromToken(token));
        User user = entityManager.find(User.class, id);
        user.setPassword(encodedPassword);
        User updatedUser = userRepository.save(user);
        if (updatedUser != null && updatedUser.getPassword().equalsIgnoreCase(encodedPassword))
            return true;
        return false;
    }

    @Override
    public void sentEmail(User user, String token) throws MessagingException {
        String recipientAddress = user.getEmail();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipientAddress);
        helper.setText("Hello " + user.getFirst_name() + "\n" + " You have requested to reset password\n" +
                "http://localhost:8084/resetpassword?json" + null + "token is:" + token + "}");
        helper.setSubject("Password_Reset_Request");
        sender.send(message);
    }
}
