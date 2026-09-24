package com.gestetner.servvista.Repositories.auditing;


import com.gestetner.servvista.Models.entity.auditing.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link AuditLog}.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
