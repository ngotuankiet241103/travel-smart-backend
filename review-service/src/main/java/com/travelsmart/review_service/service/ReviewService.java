package com.travelsmart.review_service.service;

import com.travelsmart.review_service.dto.request.ReviewImageRequest;
import com.travelsmart.review_service.dto.request.ReviewRequest;
import com.travelsmart.review_service.dto.request.ReviewUpdateRequest;
import com.travelsmart.review_service.dto.response.PageableResponse;
import com.travelsmart.review_service.dto.response.ReviewImageResponse;
import com.travelsmart.review_service.dto.response.ReviewResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewResponse create(ReviewRequest reviewRequest);

    PageableResponse<List<ReviewResponse>> getByLocationId(Long locationId, Pageable pageable);

    ReviewResponse update(Long id, ReviewUpdateRequest reviewUpdateRequest);

    ReviewImageResponse uploadImage(ReviewImageRequest reviewImageRequest);

    void deleteImage(Long id);

    Double getAverageRatingByLocation(Long locationId);
}
