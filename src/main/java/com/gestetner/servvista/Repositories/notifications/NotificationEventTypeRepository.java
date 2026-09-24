package com.gestetner.servvista.Repositories.notifications;

import com.gestetner.servvista.Models.entity.notifications.NotificationEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link NotificationEventType}.
 */
@Repository
public interface NotificationEventTypeRepository extends JpaRepository<NotificationEventType, Long> {
}
