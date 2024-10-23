package com.travelsmart.trip_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItineraryDeleteRequest {
    @NotNull(message = "TRIP_INVALID")
    private Long tripId;
}
