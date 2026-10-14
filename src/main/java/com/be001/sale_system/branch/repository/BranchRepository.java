package com.be001.sale_system.branch.repository;

import com.be001.sale_system.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
