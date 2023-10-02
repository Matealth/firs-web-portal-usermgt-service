package com.firs.wps.usermgt.repo;

import com.firs.wps.usermgt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Role findRoleByName(String name);
}
