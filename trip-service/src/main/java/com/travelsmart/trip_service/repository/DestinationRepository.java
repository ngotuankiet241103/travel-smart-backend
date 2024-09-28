package com.travelsmart.trip_service.repository;

import com.travelsmart.trip_service.entity.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<DestinationEntity,Long> {
  List<DestinationEntity> findByItineraryId(Long id);
  @Modifying
  @Query("UPDATE DestinationEntity d SET d.position = d.position - 1 WHERE d.itinerary.id = ?1 AND  d.position > ?2 AND d.position <= ?3")
  void moveDownPosition(Long sourceId, int position, int position1);
  @Modifying
  @Query("UPDATE DestinationEntity d SET d.position = d.position + 1 WHERE d.itinerary.id = ?1 AND  d.position >= ?2 AND d.position < ?3")
  void moveUpPosition(Long sourceId, int position, int position1);
  @Modifying
  @Query("UPDATE DestinationEntity d SET d.position = d.position + 1 WHERE d.itinerary.id = ?1 AND  d.position >= ?2 ")
  void updatePositionUp(Long sourceId, int position);
  @Modifying
  @Query("UPDATE DestinationEntity d SET d.position = d.position - 1 WHERE d.itinerary.id = ?1 AND  d.position > ?2 ")
  void updatePositionDown(Long sourceId, int position);

    void deleteByItineraryId(Long id);
}
