package com.gestetner.servvista.Repositories.customerportal;


import com.gestetner.servvista.Models.entity.customerportal.LoginOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link LoginOtp}.
 */
@Repository
public interface LoginOtpRepository extends JpaRepository<LoginOtp, Long> {
    Optional<LoginOtp> findFirstByPortalAccountIdAndPurposeAndConsumedAtIsNullOrderByCreatedAtDesc(Long portalAccountId, String purpose);
}
