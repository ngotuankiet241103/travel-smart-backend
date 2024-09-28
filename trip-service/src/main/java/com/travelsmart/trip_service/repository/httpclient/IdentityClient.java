package com.travelsmart.trip_service.repository.httpclient;

import com.travelsmart.trip_service.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "identity-service", url = "http://localhost:8080/identity/users")
public interface IdentityClient {
    @GetMapping("/internal/{email}/email")
    ApiResponse<String> getUserToShare(@PathVariable("email") String email);
}
