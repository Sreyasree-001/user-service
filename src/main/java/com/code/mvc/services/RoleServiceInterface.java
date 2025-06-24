package com.code.mvc.services;

import com.code.mvc.entities.Role;
import com.code.mvc.entities.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleServiceInterface {
    Optional<Role> findByRoleName(RoleName name);
//    boolean assignRole(Long id, String roleName);
//    boolean removeRole(Long id, String roleName);
//    List<String> getUserRole(Long id);
}
