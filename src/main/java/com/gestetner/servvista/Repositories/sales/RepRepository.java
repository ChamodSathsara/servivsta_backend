package com.gestetner.servvista.Repositories.sales;

import com.gestetner.servvista.Models.entity.sales.Rep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link Rep}.
 */
@Repository
public interface RepRepository extends JpaRepository<Rep, Long> {
}
