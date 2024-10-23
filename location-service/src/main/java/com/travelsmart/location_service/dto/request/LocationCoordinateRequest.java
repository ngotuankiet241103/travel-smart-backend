package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LocationCoordinateRequest {
    @NotEmpty(message = "LON_INVALID")
    private String lon;
    @NotEmpty(message = "LAT_INVALID")
    private String lat;
    @NotNull(message = "IMAGE_INVALID")
    private Long imageId;
    @NotNull(message = "TYPE_INVALID")
    private LocationType type;
}
