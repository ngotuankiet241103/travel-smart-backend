package com.travelsmart.recommend.repository;

import com.travelsmart.recommend.entity.CheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckInEntity,Long> {
    List<CheckInEntity> findByUserId(String userId);
}
