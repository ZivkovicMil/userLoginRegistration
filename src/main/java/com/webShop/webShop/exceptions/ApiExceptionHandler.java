package com.webShop.webShop.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.webShop.webShop.entities.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRegistrationTokenException.class)
    public ResponseEntity<Object> handleException(InvalidRegistrationTokenException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Object> handleException(EmailException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotRegistered.class)
    public ResponseEntity<Object> handleException(EmailNotRegistered e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Object> handleException(PasswordNotMatchException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidChangePasswordTokenException.class)
    public ResponseEntity<Object> handleException(InvalidChangePasswordTokenException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleException(TokenExpiredException e) {
        String errorCode = e.getMessage();
        String errorMessage = e.getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

}
