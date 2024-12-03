package com.travelsmart.trip_service.repository;

import com.travelsmart.trip_service.entity.UserTripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTripRepository extends JpaRepository<UserTripEntity,Long> {
    Optional<UserTripEntity> findByUserIdAndTripId(String name, Long id);

    List<UserTripEntity> findByTripId(Long id);

    void deleteByTripId(Long id);

    boolean existsByUserIdAndTripId(Long id, String userId);
}
