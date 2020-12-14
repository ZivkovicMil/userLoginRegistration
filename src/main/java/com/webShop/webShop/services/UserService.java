package com.webShop.webShop.services;

import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.EmailNotRegistered;
import com.webShop.webShop.exceptions.InvalidChangePasswordTokenException;
import com.webShop.webShop.exceptions.PasswordNotMatchException;
import com.webShop.webShop.models.UserRegister;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void saveUser(User user);

    User convertToUser(UserRegister userRegister);

    void turnOffUser(String email);

    void turnOnUser(String email);

    void sendChangePasswordEmail(String userEmail) throws EmailNotRegistered;

    void verifyAndChange(String token,String newPassword,String newPassword2) throws PasswordNotMatchException, InvalidChangePasswordTokenException;

    void changeAddress(String address, HttpServletRequest request);

    void changePhoneNumber(String phoneNumber, HttpServletRequest request);
}
