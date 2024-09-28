package com.travelsmart.trip_service.repository.httpclient;

import com.travelsmart.trip_service.dto.response.ApiResponse;
import com.travelsmart.trip_service.dto.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service", url = "http://localhost:8084/location/locations")
public interface LocationClient {
    @GetMapping("/internal/{id}")
    ApiResponse<LocationResponse> getLocationById(@PathVariable("id") Long id);
}
