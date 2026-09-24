package com.gestetner.servvista.Repositories.identity;


import com.gestetner.servvista.Models.entity.identity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link RolePermission}.
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
