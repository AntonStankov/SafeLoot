package com.example.SafeLoot.service;


import com.example.SafeLoot.entity.helpClasses.Roles;
import com.example.SafeLoot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query("select u from Role u where u.roleName=:roleName")
    List<Role> findByRoleName(@Param("roleName") Roles roleName);
}
