package com.gestetner.servvista.Repositories.identity;


import com.gestetner.servvista.Models.entity.identity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Permission}.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
