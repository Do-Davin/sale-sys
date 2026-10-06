package com.tv_dd.sale_system.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tv_dd.sale_system.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
