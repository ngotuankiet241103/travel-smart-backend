package com.travelsmart.media_service.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.travelsmart.media_service.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadCloud(MultipartFile file,String publicId) throws IOException {
        return cloudinary.uploader()
                .upload(file.getBytes(), Map.of("public_id",publicId))
                .get("url")
                .toString();
    }

    @Override
    public void removeImage(String publicId) {
        try {
           cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
