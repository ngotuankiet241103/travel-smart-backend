package com.travelsmart.profile_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProfileUpdateRequest {
    @NotEmpty(message = "FIRST_NAME_INVALID")
    private String firstName;
    @NotEmpty(message = "LAST_NAME_INVALID")
    private String lastName;

}
