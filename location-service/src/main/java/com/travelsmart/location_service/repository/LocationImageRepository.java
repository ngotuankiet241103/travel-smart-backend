package com.travelsmart.location_service.repository;

import com.travelsmart.location_service.entity.LocationImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationImageRepository extends JpaRepository<LocationImageEntity,Long> {
}
