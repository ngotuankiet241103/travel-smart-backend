package com.travelsmart.location_service.repository.httpclient;

import com.travelsmart.location_service.config.LocationMediaInterceptor;
import com.travelsmart.location_service.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service", url = "http://localhost:8086/review/reviews")
public interface ReviewClient {
    @GetMapping("/internal/{location-id}/location")
    ApiResponse<Double> getStarRateByLocation(@PathVariable("location-id") Long locationId);
}