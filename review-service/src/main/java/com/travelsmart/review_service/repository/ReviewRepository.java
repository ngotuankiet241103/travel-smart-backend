package com.travelsmart.review_service.repository;

import com.travelsmart.review_service.dto.response.ReviewResponse;
import com.travelsmart.review_service.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
    Page<ReviewEntity> findByLocationId(Pageable pageable, Long locationId);
    @Query(value = "SELECT AVG(r.star_rate) FROM review r  WHERE r.location_id =?1 ",nativeQuery = true)
    Double averageRatingByLocation(Long locationId);

    Optional<ReviewEntity> findByLocationIdAndUserId(Long locationId, String name);
}
