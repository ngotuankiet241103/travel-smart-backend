package com.travelsmart.trip_service.repository.httpclient;

import com.travelsmart.trip_service.constant.LocationType;
import com.travelsmart.trip_service.dto.response.ApiResponse;
import com.travelsmart.trip_service.dto.response.LocationResponse;
import jakarta.security.auth.message.callback.PasswordValidationCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "location-service", url = "${feign.url.location}/location/locations")
public interface LocationClient {
    @GetMapping("/internal/{id}")
    ApiResponse<LocationResponse> getLocationById(@PathVariable("id") Long id);


    @GetMapping("/internal/type")
    ApiResponse<List<LocationResponse>> getByTypes(@RequestParam("types") List<LocationType> types,
                                                   @RequestParam("locationId") Long locationId,
                                                   @RequestParam("total_day") int day);
}
