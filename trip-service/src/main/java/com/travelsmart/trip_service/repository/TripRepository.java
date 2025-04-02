package com.travelsmart.trip_service.repository;

import com.travelsmart.trip_service.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Date;
import java.util.List;

public interface TripRepository extends JpaRepository<TripEntity,Long> {
    @Query("SELECT t FROM TripEntity t JOIN UserTripEntity u ON t.id = u.trip.id WHERE u.userId = ?1 ORDER BY t.id DESC")
    List<TripEntity> findMyTrip(String name);
    @Query("SELECT count(t.id) FROM TripEntity t WHERE t.createdDate >= ?1 AND t.createdDate <= ?2")
    long countByCreatedDate(Date startOfDay, Date endOfDay);
    @Query("SELECT COUNT(l) AS count " +
            "FROM TripEntity l " +
            "WHERE YEAR(l.createdDate) = ?1  AND MONTH(l.createdDate) = ?2 "
    )
    long countByMonthInYear(Integer year, int i);
    @Query("SELECT l AS count " +
            "FROM TripEntity l " +
            "WHERE YEAR(l.createdDate) = ?1  AND MONTH(l.createdDate) = ?2 ")
    List<TripEntity> findByYearAndMonth(Integer year, Integer month);
}
