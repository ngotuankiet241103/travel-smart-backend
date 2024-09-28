package com.travelsmart.profile_service.repository;

import com.travelsmart.profile_service.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,String> {
    Optional<ProfileEntity> findByEmail(String email);
}
