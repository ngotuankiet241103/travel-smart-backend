package com.travelsmart.profile_service.repository.httpclient;


import com.travelsmart.profile_service.dto.response.ApiResponse;
import com.travelsmart.profile_service.dto.response.MediaHttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "media-service", url = "http://localhost:8088/api/v1/medias")
public interface MediaClient {
    @GetMapping("/{id}")
    ApiResponse<MediaHttpResponse> getById(@PathVariable("id") Long id);
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<MediaHttpResponse> uploadFile(@RequestPart("file")MultipartFile file);
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteById(@PathVariable("id") Long id);
}
