package com.gestetner.servvista.Repositories.customerportal;


import com.gestetner.servvista.Models.entity.customerportal.PortalRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link PortalRefreshToken}.
 */
@Repository
public interface PortalRefreshTokenRepository extends JpaRepository<PortalRefreshToken, Long> {
}
