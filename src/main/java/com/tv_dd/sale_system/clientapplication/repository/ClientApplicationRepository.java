package com.tv_dd.sale_system.clientapplication.repository;

import java.util.Optional;
import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientApplicationRepository extends JpaRepository<ClientApplication, Integer> {
    Optional<ClientApplication> findByName(String name);
}
