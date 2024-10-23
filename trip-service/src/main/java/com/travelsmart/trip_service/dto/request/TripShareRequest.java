package com.travelsmart.trip_service.dto.request;

import com.travelsmart.trip_service.constant.TripPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TripShareRequest {
    @NotEmpty(message = "EMAIL_INVALID")
    private String email;
    @NotNull(message = "PERMISSION_INVALID")
    private TripPermission tripPermission;
}
