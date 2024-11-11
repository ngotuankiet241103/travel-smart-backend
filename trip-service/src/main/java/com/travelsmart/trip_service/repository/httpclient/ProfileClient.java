package com.travelsmart.trip_service.repository.httpclient;


import com.travelsmart.trip_service.dto.response.ApiResponse;
import com.travelsmart.trip_service.dto.response.httpclient.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", url = "http://localhost:8081/profile/users")
public interface ProfileClient {
    @GetMapping("/internal/profile/{user-id}")
    ApiResponse<ProfileResponse> getUserById(@PathVariable("user-id") String id);
}
