package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationStatus;
import lombok.Data;

@Data
public class LocationStatusRequest {
    private LocationStatus status;
}
