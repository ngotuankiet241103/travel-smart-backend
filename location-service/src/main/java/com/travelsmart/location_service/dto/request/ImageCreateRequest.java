package com.travelsmart.location_service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ImageCreateRequest {
    private MultipartFile file;
    private String caption;
}
