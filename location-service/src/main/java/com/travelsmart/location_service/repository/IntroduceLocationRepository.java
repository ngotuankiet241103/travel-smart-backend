package com.travelsmart.location_service.repository;

import com.travelsmart.location_service.entity.IntroduceLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IntroduceLocationRepository extends JpaRepository<IntroduceLocationEntity,Long> {
    @Query(value = "SELECT * FROM introduce_location WHERE location_place_id = ?1",nativeQuery = true)
    Optional<IntroduceLocationEntity> findByLocationId(Long locationId);
}
