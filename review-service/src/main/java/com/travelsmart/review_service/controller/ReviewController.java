package com.travelsmart.review_service.controller;

import com.travelsmart.review_service.dto.request.ReviewImageRequest;
import com.travelsmart.review_service.dto.request.ReviewRequest;
import com.travelsmart.review_service.dto.request.ReviewUpdateRequest;
import com.travelsmart.review_service.dto.response.ApiResponse;
import com.travelsmart.review_service.dto.response.PageableResponse;
import com.travelsmart.review_service.dto.response.ReviewImageResponse;
import com.travelsmart.review_service.dto.response.ReviewResponse;
import com.travelsmart.review_service.service.ReviewService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @Operation(summary = "Create new review for location",description = "Returns single review")
    @PostMapping
    public ApiResponse<ReviewResponse> create(@RequestBody ReviewRequest reviewRequest){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.create(reviewRequest))
                .build();
    }
    @Operation(summary = "Get reviews by location id",description = "Returns reviews")
    @GetMapping("/{location-id}/location")
    public ApiResponse<PageableResponse<List<ReviewResponse>>> getByLocationId(@PathVariable("location-id") Long locationId,
                                                                               @RequestParam(value = "page",defaultValue = "1") int page,
                                                                               @RequestParam(value = "limit",defaultValue = "6") int limit){
        Pageable pageable = PageRequest.of(page - 1,limit);
        return ApiResponse.<PageableResponse<List<ReviewResponse>>>builder()
                .result(reviewService.getByLocationId(locationId,pageable))
                .build();
    }
    @Operation(summary = "Get detail review by location and user",description = "Returns single review")
    @GetMapping("/detail/{location-id}")
    public ApiResponse<ReviewResponse> getDetailReviewByUser(@PathVariable("location-id") Long locationId,
                                                             Authentication authentication){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.getDetailReviewByUser(locationId,authentication.getName()))
                .build();
    }
    @Operation(summary = "Update review",description = "Returns single review")
    @PutMapping("/{id}")
    public ApiResponse<ReviewResponse> update(@PathVariable("id") Long id,
                                              @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.update(id,reviewUpdateRequest))
                .build();
    }
    @Operation(summary = "Upload image review",description = "Returns single image")
    @PostMapping("/image")
    public ApiResponse<ReviewImageResponse> uploadImage(@ModelAttribute ReviewImageRequest reviewImageRequest){
        return ApiResponse.<ReviewImageResponse>builder()
                .result(reviewService.uploadImage(reviewImageRequest))
                .build();
    }
    @Operation(summary = "Delete image review by id")
    @DeleteMapping("/image/{image-id}")
    public ApiResponse<Void> deleteImage(@PathVariable("image-id") Long id){
        reviewService.deleteImage(id);
        return ApiResponse.<Void>builder().build();
    }
    @Hidden
    @GetMapping("/internal/{location-id}/location")
    public ApiResponse<Double> getAverageRatingByLocation(@PathVariable("location-id") Long locationId){
        return ApiResponse.<Double>builder()
                .result(reviewService.getAverageRatingByLocation(locationId))
                .build();
    }


}
