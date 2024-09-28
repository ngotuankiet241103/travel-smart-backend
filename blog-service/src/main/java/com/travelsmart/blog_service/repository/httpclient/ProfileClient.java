package com.travelsmart.blog_service.repository.httpclient;

import com.travelsmart.blog_service.dto.response.ApiResponse;
import com.travelsmart.blog_service.dto.response.ProfileUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "media-service", url = "http://localhost:8081/profile/users")
public interface ProfileClient {
    @GetMapping("/internal/profile/{user-id}")
    ApiResponse<ProfileUserResponse> getUserById(@PathVariable("user-id") String id);
}
