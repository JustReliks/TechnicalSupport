package org.TechnicalSupport.repository;

import org.TechnicalSupport.entity.Role;
import org.TechnicalSupport.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleEnum(UserRole userRole);
}
