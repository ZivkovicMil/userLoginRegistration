package com.webShop.webShop.controllers;

import com.webShop.webShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/changeAddress/{newAddress}", method = RequestMethod.POST)
    public ResponseEntity changeAddress(@PathVariable("newAddress") String newAddress, HttpServletRequest request) {
        userService.changeAddress(newAddress, request);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/changePhoneNumber/{phoneNumber}", method = RequestMethod.POST)
    public ResponseEntity changePhoneNumber(@PathVariable("phoneNumber") String phoneNumber, HttpServletRequest request) {
        userService.changeAddress(phoneNumber, request);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
