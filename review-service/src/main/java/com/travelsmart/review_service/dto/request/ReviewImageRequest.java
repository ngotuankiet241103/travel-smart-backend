package com.travelsmart.review_service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ReviewImageRequest {
    private MultipartFile file;
}
