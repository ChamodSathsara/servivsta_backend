package com.gestetner.servvista.Repositories.organization;

import com.gestetner.servvista.Models.entity.organization.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for {@link UserCompany}.
 */
@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, Long> {
    List<UserCompany> findAllByUserIdIn(Collection<Long> userIds);

    List<UserCompany> findAllByUserIdOrderByUserCompanyId(Long userId);
}
