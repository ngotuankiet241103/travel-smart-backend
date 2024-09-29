package com.travelsmart.review_service.repository;

import com.travelsmart.review_service.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity,Long> {
    List<ReviewImageEntity> findByReviewId(Long id);
}
