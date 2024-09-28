package com.travelsmart.profile_service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class AvatarRequest {
    private MultipartFile file;
}
