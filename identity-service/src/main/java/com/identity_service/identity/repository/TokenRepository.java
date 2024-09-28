package com.identity_service.identity.repository;

import com.identity_service.identity.entity.TokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity,String> {
    @Transactional
    @Modifying
    @Query("UPDATE TokenEntity t SET t.isExpired = true WHERE t.token = ?1 ")
    void expiredToken(String jwtid);

    boolean existsByToken(String jwtid);

    Optional<TokenEntity> findByToken(String token);
    @Transactional
    @Modifying
    @Query("UPDATE TokenEntity t SET t.isExpired = true WHERE t.user.id = ?1 ")
    void expiredAll(String id);
}
