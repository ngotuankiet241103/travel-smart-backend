package com.travelsmart.review_service.repository.httpclient;

import com.travelsmart.review_service.dto.response.ApiResponse;
import com.travelsmart.review_service.dto.response.httpclient.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "location-service", url = "${feign.url.location}/location/locations")
public interface LocationClient {
    @GetMapping("/internal/{id}")
    ApiResponse<LocationResponse> getLocationById(@PathVariable("id") Long id);



}
