package com.travelsmart.review_service.mapper;

import com.travelsmart.review_service.dto.request.ReviewRequest;
import com.travelsmart.review_service.dto.response.ReviewResponse;
import com.travelsmart.review_service.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "userId",ignore = true)
    ReviewEntity toReviewEntity(ReviewRequest reviewRequest);
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "images",ignore = true)
    ReviewResponse toReviewResponse(ReviewEntity reviewEntity);
}
