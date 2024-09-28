package com.travelsmart.trip_service.repository;

import com.travelsmart.trip_service.entity.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ItineraryRepository extends JpaRepository<ItineraryEntity,Long> {
    List<ItineraryEntity> findByTripId(Long id);
    @Modifying
    @Query("DELETE FROM ItineraryEntity i WHERE i.trip.id = ?1 AND i.day < ?2 OR i.day > ?3")
    void deleteArrange(Long id, Date startDate, Date endDate);
}
