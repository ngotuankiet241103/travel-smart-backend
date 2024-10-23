package com.travelsmart.trip_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DestinationDeleteRequest {
    @NotNull(message = "ITINERARY_INVALID")
    private Long itineraryId;
}
