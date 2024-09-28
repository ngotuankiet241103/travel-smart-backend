package com.travelsmart.trip_service.repository;

import com.travelsmart.trip_service.dto.response.TripResponse;
import com.travelsmart.trip_service.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<TripEntity,Long> {
    @Query("SELECT t FROM TripEntity t JOIN UserTripEntity u ON t.id = u.trip.id WHERE u.userId = ?1")
    List<TripEntity> findMyTrip(String name);
}
