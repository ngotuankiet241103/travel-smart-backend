package com.travelsmart.profile_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileUpdateAvatar {
    @NotNull(message = "ID_INVALID")
    private Long id;
}
