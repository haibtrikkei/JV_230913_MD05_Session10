package com.rikkeiacademy.demo_jwt_security_api.repository;

import com.rikkeiacademy.demo_jwt_security_api.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findRoleByRoleName(String roleName);
}
