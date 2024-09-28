package com.travelsmart.media_service.controller;

import com.travelsmart.media_service.dto.request.MediaRequest;
import com.travelsmart.media_service.dto.response.ApiResponse;
import com.travelsmart.media_service.dto.response.MediaResponse;
import com.travelsmart.media_service.service.MediaService;
import lombok.Builder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/medias")
@Builder
public class MediaController {
    private final MediaService mediaService;
    @PostMapping("/upload")
    public ApiResponse<MediaResponse> uploadFile(@RequestPart("file")MultipartFile file) throws IOException {
        MediaRequest mediaRequest = MediaRequest.builder()
                .file(file)
                .build();
        return ApiResponse.<MediaResponse>builder()
                .result(mediaService.uploadFile(mediaRequest))
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<MediaResponse> getById(@RequestParam("id") Long id) throws IOException {
        return ApiResponse.<MediaResponse>builder()
                .result(mediaService.findById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable("id") Long id) throws IOException {
        mediaService.deleteById(id);
        return ApiResponse.<Void>builder()

                .build();
    }
}
