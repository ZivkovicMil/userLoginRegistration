package com.webShop.webShop.services;

import com.webShop.webShop.entities.Role;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.repository.RoleRepository;
import com.webShop.webShop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
        log.info(role.getRole_name() + " is added into database");
    }

    @Override
    public Set<Role> addUserRoleToUser() {
        Set<Role> set = new HashSet<>();
        set.add(roleRepository.findRole("USER"));
        log.info("Role USER is added to user");
        return set;
    }

    @Override
    public void addRoleToUser(User user, String role) {
        Role addingRole = roleRepository.findRole(role);
        if (addingRole != null) {
            user.getList_of_roles().add(addingRole);
            userRepository.save(user);
            log.info(role + " is added to " + user.getEmail());
        } else {
            log.info("Role doesn't exist");
        }
    }

    @Override
    public void removeRoleFromUser(User user, String role) {
        Set<Role> roles = user.getList_of_roles();
        for (Role r : roles) {
            if (r.getRole_name().equals(role)) {
                roles.remove(r);
                userRepository.save(user);
                log.info(role + " is removed from user with email: " + user.getEmail());
                break;
            }
        }
    }
}