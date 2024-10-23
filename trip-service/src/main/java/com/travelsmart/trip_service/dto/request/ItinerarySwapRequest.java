package com.travelsmart.trip_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItinerarySwapRequest {
    @NotNull(message = "DESTINATION_FROM_INVALID")
    private Long fromId;
    private Long toId;
    @NotNull(message = "ITINERARY_FROM_INVALID")
    private Long sourceId;
    @NotNull(message = "ITINERARY_TO_INVALID")
    private Long destinationId;
}
