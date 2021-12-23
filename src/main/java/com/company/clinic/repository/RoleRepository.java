package com.company.clinic.repository;

import com.company.clinic.model.security.Role;
import com.company.clinic.model.security.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}