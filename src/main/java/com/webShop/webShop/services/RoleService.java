package com.webShop.webShop.services;

import com.webShop.webShop.entities.Role;
import com.webShop.webShop.entities.User;

import java.util.Set;

public interface RoleService {
    void addRole(Role role);

    Set<Role> addUserRoleToUser();

    void addRoleToUser(User user, String role);

    void removeRoleFromUser(User user, String role);
}
