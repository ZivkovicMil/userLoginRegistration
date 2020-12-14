package com.webShop.webShop;

import java.util.ResourceBundle;

public enum Messages {

    INVALID_USERNAME_OR_PASSWORD("9001", "error.invalid_username_or_password"),
    EMAIL_IS_ALREADY_TAKEN("9002", "error.used_email"),
    INVALID_REGISTRATION_TOKEN("9003", "error.invalid_registration_token"),
    EMAIL_NOT_REGISTERED("9004","error.email_doesnt_exist"),
    PASSWORD_DONT_MATCH("9005","error.password_dont_match"),
    CHANGE_PASSWORD_TOKEN_DOESNT_EXIST("9006","error.invalid_change_password_token"),
    CHANGE_PASSWORD_TOKEN_IS_EXPIRED("9007","error.change_password_token_expired"),
    REGISTRATION_TOKEN_IS_EXPIRED("9008","error.registration_token_expired");

    private static final ResourceBundle errorMessageBundle = ResourceBundle.getBundle("messages");

    private final String identification;
    private final String messagePropertyKey;
    private final Object[] formatArguments;

    Messages(String identification, String messagePropertyKey, Object... formatArgs) {
        this.identification = identification;
        this.messagePropertyKey = messagePropertyKey;
        this.formatArguments = formatArgs;
    }

    public String getIdentification() {
        return identification;
    }

    public String getMessage() {
        return String.format(errorMessageBundle.getString(messagePropertyKey), formatArguments);
    }
}
