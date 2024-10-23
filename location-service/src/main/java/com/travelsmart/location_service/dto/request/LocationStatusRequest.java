package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationStatusRequest {
    @NotNull(message = "STATUS_INVALID")
    private LocationStatus status;
}
