package com.travelsmart.media_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadCloud(MultipartFile file,String publicId) throws IOException;

    void removeImage(String publicId);
}
