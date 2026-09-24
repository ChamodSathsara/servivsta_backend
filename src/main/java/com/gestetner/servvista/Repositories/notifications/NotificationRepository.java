package com.gestetner.servvista.Repositories.notifications;

import com.gestetner.servvista.Models.entity.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Notification}.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
