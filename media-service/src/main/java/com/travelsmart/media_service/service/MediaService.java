package com.travelsmart.media_service.service;

import com.travelsmart.media_service.dto.request.MediaRequest;
import com.travelsmart.media_service.dto.response.MediaResponse;

import java.io.IOException;

public interface MediaService {
    MediaResponse uploadFile(MediaRequest mediaRequest) throws IOException;

    MediaResponse findById(Long id);

    void deleteById(Long id);
}
