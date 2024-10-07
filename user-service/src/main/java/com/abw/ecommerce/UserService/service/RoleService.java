package com.abw.ecommerce.UserService.service;

import com.abw.ecommerce.UserService.model.entity.Role;
import com.abw.ecommerce.UserService.model.entity.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleName name);
    boolean assignRole(Long id, String roleName);
    boolean revokeRole(Long id, String roleName);
    List<String> getUserRoles(Long id);
}
