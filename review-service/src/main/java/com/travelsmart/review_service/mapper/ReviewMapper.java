package com.travelsmart.review_service.mapper;

import com.travelsmart.review_service.dto.request.ReviewRequest;
import com.travelsmart.review_service.dto.response.ReviewResponse;
import com.travelsmart.review_service.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toReviewEntity(ReviewRequest reviewRequest);
    ReviewResponse toReviewResponse(ReviewEntity reviewEntity);
}
