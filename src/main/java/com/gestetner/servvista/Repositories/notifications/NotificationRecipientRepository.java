package com.gestetner.servvista.Repositories.notifications;

import com.gestetner.servvista.Models.entity.notifications.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link NotificationRecipient}.
 */
@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
}
