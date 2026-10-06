package com.tv_dd.sale_system.role.repository;

import java.util.Optional;
import com.tv_dd.sale_system.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
