package com.code.mvc.repositories;

import com.code.mvc.entities.Role;
import com.code.mvc.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByRoleName(@Param("name") RoleName name);

//    @Query("SELECT u.roles FROM User u WHERE u.id = :id")
 //   List<Role> findByRoleId(@Param("id") Long id);
}
