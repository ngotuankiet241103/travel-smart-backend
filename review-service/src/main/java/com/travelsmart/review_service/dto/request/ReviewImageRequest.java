package com.travelsmart.review_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ReviewImageRequest {
    @NotNull(message = "FILE_INVALID")
    private MultipartFile file;
}
