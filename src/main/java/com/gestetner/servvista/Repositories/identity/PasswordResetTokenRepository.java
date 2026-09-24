package com.gestetner.servvista.Repositories.identity;


import com.gestetner.servvista.Models.entity.identity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link PasswordResetToken}.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
