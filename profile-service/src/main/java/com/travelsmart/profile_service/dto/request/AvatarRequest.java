package com.travelsmart.profile_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class AvatarRequest {
    @NotNull(message = "FILE_INVALID")
    private MultipartFile file;
}
