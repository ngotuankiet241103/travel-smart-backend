package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationType;
import lombok.Data;

import java.util.List;

@Data
public class LocationCoordinateRequest {
    private String lon;
    private String lat;
    private Long imageId;
    private LocationType type;
}
