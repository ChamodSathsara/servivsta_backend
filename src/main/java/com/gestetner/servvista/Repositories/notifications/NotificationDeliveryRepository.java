package com.gestetner.servvista.Repositories.notifications;

import com.gestetner.servvista.Models.entity.notifications.NotificationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link NotificationDelivery}.
 */
@Repository
public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, Long> {
}
