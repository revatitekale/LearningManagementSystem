package com.bl.lms.service;

import com.bl.lms.configuration.ApplicationConfig;
import com.bl.lms.dto.EmailDTO;
import com.bl.lms.dto.LoginDTO;
import com.bl.lms.dto.UserDTO;
import com.bl.lms.exception.LmsAppException;
import com.bl.lms.model.User;
import com.bl.lms.repository.UserRepository;
import com.bl.lms.util.JwtToken;
import com.bl.lms.util.RabbitMq;
import com.bl.lms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
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
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Value("${spring.redis.key}")
    private static final String REDIS_KEY = "key";

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
    private RabbitMq rabbitMq;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailDTO mailDto;

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
        UserDTO user = userRepository.findByEmail(email);
        final String token = jwtToken.generatePasswordResetToken(String.valueOf(user.getId()));
        sentEmail(user);
        return token;
    }

    /**
     * @param userDTO, token
     * @return Method to send reset password request email.
     * @throws MessagingException
     */
    public void sentEmail(UserDTO userDTO) throws MessagingException {
        try {
            User user = userRepository.findByEmail(userDTO.getEmail())
                    .orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType.USER_NOT_FOUND, "User not found"));

            mailDto.setTo(userDTO.getEmail());
            mailDto.setBody("Hello " + userDTO.getFirstName() + " Reset your password " +
                    "Link: http://localhost:8084/resetpassword " + "Use your email and a new password" +
                    "token: " + jwtToken.generateToken(user.getId()));
            mailDto.setSubject("Regarding reset password");
            mailDto.setFrom("revitekale1910@gmail.com");
            rabbitMq.sendMail(mailDto);
        } catch (LmsAppException e) {
            throw new LmsAppException(LmsAppException.exceptionType.DATA_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * @param loginDto
     * @return response(Method to login user and store token into redis database)
     */
    @Override
    public Map<Object, Object> loginUser(LoginDTO loginDto) throws Exception {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new LmsAppException(LmsAppException.exceptionType
                .INVALID_EMAIL_ID, "User not found"));
        String authenticationToken = getAuthenticationToken(user.getEmail(), loginDto.getPassword());
        redisUtil.save(REDIS_KEY, user.getEmail(), authenticationToken);
        return redisUtil.getValue(REDIS_KEY);
    }

    /**
     * @param userName, password
     * @return Method to get authentication token
     * @throws Exception
     */
    @Override
    public String getAuthenticationToken(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            final UserDetails userDetails = userService.loadUserByUsername(userName);
            final String token = jwtToken.generateToken(userDetails);
            return token;
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
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
