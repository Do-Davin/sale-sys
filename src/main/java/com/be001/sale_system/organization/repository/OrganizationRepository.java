package com.be001.sale_system.organization.repository;

import com.be001.sale_system.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
