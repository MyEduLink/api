package com.myedulink.backend.repositories;

import com.myedulink.backend.model.UserRole;
import com.myedulink.backend.model.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
