package com.travelsmart.trip_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItineraryRequest {
    @NotNull(message = "LOCATION_INVALID")
    private Long locationId;
}
