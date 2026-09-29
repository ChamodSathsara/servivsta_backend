package com.gestetner.servvista.Repositories.notifications;

import com.gestetner.servvista.Models.Enums.Notifications.NotificationEventCode;
import com.gestetner.servvista.Models.entity.notifications.NotificationEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link NotificationEventType}.
 */
@Repository
public interface NotificationEventTypeRepository extends JpaRepository<NotificationEventType, Long> {

    boolean existsByEventCode(NotificationEventCode eventCode);

    boolean existsByEventCodeAndNotificationEventTypeIdNot(
            NotificationEventCode eventCode, Long notificationEventTypeId);
}
