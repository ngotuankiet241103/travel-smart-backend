package com.travelsmart.profile_service.repository;

import com.travelsmart.profile_service.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<AvatarEntity,Long> {
    AvatarEntity findByProfileId(String id);
}
