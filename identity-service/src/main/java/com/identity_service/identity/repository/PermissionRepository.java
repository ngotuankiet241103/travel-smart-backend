package com.identity_service.identity.repository;

import com.identity_service.identity.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity,String> {
    Optional<PermissionEntity> findByName(String name);

}
