package com.webShop.webShop.services;

public interface EmailService {

    void sendRegistrationEmail(String email, String token);
    void sendChangePasswordEmail(String email, String token);
}
