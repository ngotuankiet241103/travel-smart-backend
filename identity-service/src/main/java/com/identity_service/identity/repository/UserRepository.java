package com.identity_service.identity.repository;

import com.identity_service.identity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String username);

    boolean existsByEmail(String email);


}
