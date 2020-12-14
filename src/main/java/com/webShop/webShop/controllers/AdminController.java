package com.webShop.webShop.controllers;

import com.webShop.webShop.entities.Role;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.repository.UserRepository;
import com.webShop.webShop.services.RoleService;
import com.webShop.webShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity allUsers(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return new ResponseEntity(userPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/addRole/{role}")
    public ResponseEntity addNewRole(@PathVariable("role") String role) {
        roleService.addRole(new Role(role));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "role/addAdmin/{email}", method = RequestMethod.POST)
    public ResponseEntity addAdminRoleToUser(@PathVariable("email") String useremail) {
        User user = userRepository.findByEmail(useremail);
        if (user != null) {
            roleService.addRoleToUser(user, "ADMIN");
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "role/removeRole/{email}/{role}", method = RequestMethod.POST)
    public ResponseEntity removeRoleFromUser(@PathVariable("email") String userEmail, @PathVariable("role") String role) {
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            roleService.removeRoleFromUser(user, role);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "turnOffUser/{email}", method = RequestMethod.POST)
    public ResponseEntity turnOffUser(@PathVariable("email") String email) {
        userService.turnOffUser(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "turnOnUser/{email}", method = RequestMethod.POST)
    public ResponseEntity turnOnUser(@PathVariable("email") String email) {
        userService.turnOffUser(email);
        return new ResponseEntity(HttpStatus.OK);
    }
}
