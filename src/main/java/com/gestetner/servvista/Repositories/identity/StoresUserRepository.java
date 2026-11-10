package com.gestetner.servvista.Repositories.identity;

import com.gestetner.servvista.Models.entity.identity.StoresUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoresUserRepository extends JpaRepository<StoresUser, Long> {
    Optional<StoresUser> findByUserId(Long userId);

    @Query("select storesUser from StoresUser storesUser join fetch storesUser.user order by storesUser.storesUserId")
    List<StoresUser> findAllWithUser();
}
