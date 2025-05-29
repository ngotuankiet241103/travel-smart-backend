package com.identity_service.identity.repository.httpclient;

import com.identity_service.identity.dto.response.ApiResponse;
import com.identity_service.identity.dto.response.httpclient.ProfileUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", url = "${feign.url.profile}/profile/users")
public interface ProfileClient {
    @GetMapping("/internal/profile/{user-id}")
    ApiResponse<ProfileUserResponse> getUserById(@PathVariable("user-id") String id);
}
