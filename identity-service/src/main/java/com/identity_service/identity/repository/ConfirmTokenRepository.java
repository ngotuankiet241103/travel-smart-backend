package com.identity_service.identity.repository;

import com.identity_service.identity.constaint.ConfirmTokenType;
import com.identity_service.identity.entity.ConfirmTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmTokenEntity,String> {
    ConfirmTokenEntity findByToken(String token);

    boolean existsByToken(String token);

    Optional<ConfirmTokenEntity> findByTokenAndType(String token, ConfirmTokenType confirmTokenType);
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmTokenEntity c SET c.isExpired = true WHERE c.user.id = ?1 AND c.type=?2")
    void expiredAllTokenForgot(String id, ConfirmTokenType confirmTokenType);
}
