package com.travelsmart.trip_service.repository;

import com.travelsmart.trip_service.entity.DestinationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface DestinationRepository extends JpaRepository<DestinationEntity,Long> {
  List<DestinationEntity> findByItineraryIdOrderByPosition(Long id);
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
  @Transactional
  @Modifying
  @Query("UPDATE DestinationEntity SET position = ?2 WHERE id = ?1")
  void updatePositionById(Long destinationId, int position);
}
