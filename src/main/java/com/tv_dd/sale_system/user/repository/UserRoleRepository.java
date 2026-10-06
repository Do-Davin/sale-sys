package com.tv_dd.sale_system.user.repository;

import java.util.List;
import com.tv_dd.sale_system.user.model.UserRole;
import com.tv_dd.sale_system.user.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(Integer userId);
}
