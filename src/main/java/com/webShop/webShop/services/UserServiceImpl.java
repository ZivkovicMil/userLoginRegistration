package com.webShop.webShop.services;

import com.webShop.webShop.Messages;
import com.webShop.webShop.entities.ChangePasswordToken;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.EmailNotRegistered;
import com.webShop.webShop.exceptions.InvalidChangePasswordTokenException;
import com.webShop.webShop.exceptions.PasswordNotMatchException;
import com.webShop.webShop.models.UserRegister;
import com.webShop.webShop.repository.ChangePasswordTokenRepository;
import com.webShop.webShop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User convertToUser(UserRegister userRegister) {
        User user = new User(userRegister.getUsername(),
                bcryptEncoder.encode(userRegister.getPassword()),
                userRegister.getEmail(),
                userRegister.getFirst_name(),
                userRegister.getLast_name(),
                userRegister.getAddress(),
                userRegister.getPhone_number());
        user.setList_of_roles(roleService.addUserRoleToUser());
        return user;
    }

    @Override
    public void turnOffUser(String email) {
        User user = userRepository.findByEmail(email);
        user.setIs_enabled(false);
        userRepository.save(user);
        log.info("User active status with email: " + user.getEmail() + " is changed to false");
    }

    @Override
    public void turnOnUser(String email) {
        User user = userRepository.findByEmail(email);
        user.setIs_enabled(true);
        userRepository.save(user);
        log.info("User active status with email: " + user.getEmail() + " is changed to true");
    }

    @Override
    public void sendChangePasswordEmail(String userEmail) throws EmailNotRegistered {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new EmailNotRegistered(Messages.EMAIL_NOT_REGISTERED);
        }
        String token = generateToken();

        changePasswordTokenRepository.save(new ChangePasswordToken(token, user));
        emailService.sendChangePasswordEmail(userEmail, token);
    }

    @Override
    public void verifyAndChange(String token, String newPassword, String newPassword2) throws PasswordNotMatchException, InvalidChangePasswordTokenException {
        try {
            ChangePasswordToken cpt = changePasswordTokenRepository.findByToken(token);
            User user = ((cpt != null) ? cpt.getUser() : null);
            if (user != null) {
                if (!tokenService.checkChangePasswordTokenExpiration(cpt)) {
                    log.info("Change password token for user: " + user.getEmail() + " is expired");
                    throw new InvalidChangePasswordTokenException(Messages.CHANGE_PASSWORD_TOKEN_IS_EXPIRED);
                }
                if (!newPassword.equals(newPassword2)) {
                    log.info("Passwords don't match");
                    throw new PasswordNotMatchException(Messages.PASSWORD_DONT_MATCH);
                }
                user.setPassword(bcryptEncoder.encode(newPassword));
                changePasswordTokenRepository.delete(cpt);
                log.info("Change password token for user: " + user.getEmail() + " is deleted from database");
                userRepository.save(user);
                log.info("Password for user: " + user.getEmail() + " is changed");
            } else {
                log.info("Change password token doesn't exist in database");
                throw new InvalidChangePasswordTokenException(Messages.CHANGE_PASSWORD_TOKEN_DOESNT_EXIST);
            }
        } catch (PasswordNotMatchException e) {
            throw new PasswordNotMatchException(Messages.PASSWORD_DONT_MATCH);
        } catch (InvalidChangePasswordTokenException ee) {
            throw (ee.getMessages().equals(Messages.CHANGE_PASSWORD_TOKEN_IS_EXPIRED) ? new InvalidChangePasswordTokenException(Messages.CHANGE_PASSWORD_TOKEN_IS_EXPIRED) : new InvalidChangePasswordTokenException(Messages.CHANGE_PASSWORD_TOKEN_DOESNT_EXIST));
        }
    }

    @Override
    public void changeAddress(String address, HttpServletRequest request) {
        User user = findUser(request);
        user.setAddress(address);
        userRepository.save(user);
        log.info("User with email: " + user.getEmail() + " is changed his address into " + address);
    }

    @Override
    public void changePhoneNumber(String phoneNumber, HttpServletRequest request) {
        User user = findUser(request);
        user.setAddress(phoneNumber);
        userRepository.save(user);
        log.info("User with email: " + user.getEmail() + " is changed his phone number into " + phoneNumber);
    }


    private String generateToken() {
        String registrationToken = UUID.randomUUID().toString();
        log.info("Token is generated" + registrationToken);
        return registrationToken;
    }

    private User findUser(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        String userEmail = userPrincipal.getName();
        User user = userRepository.findByEmail(userEmail);
        return user;
    }
}
