package com.travelsmart.location_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ImageCreateRequest {
    @NotNull(message = "FILE_INVALID")
    private MultipartFile file;
    private String caption;
}
