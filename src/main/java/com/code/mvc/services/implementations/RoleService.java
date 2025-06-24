package com.code.mvc.services.implementations;

import com.code.mvc.entities.Role;
import com.code.mvc.entities.RoleName;
import com.code.mvc.repositories.RoleRepository;
import com.code.mvc.repositories.UserRepository;
import com.code.mvc.services.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements RoleServiceInterface {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public Optional<Role> findByRoleName(RoleName name){
        return Optional.ofNullable(roleRepository.findByRoleName(name))
                .orElseThrow(() -> new RuntimeException("No role found with name: " + name));
    }
}
