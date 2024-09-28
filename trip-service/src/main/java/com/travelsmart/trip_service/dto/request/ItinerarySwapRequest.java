package com.travelsmart.trip_service.dto.request;

import lombok.Data;

@Data
public class ItinerarySwapRequest {
    private Long fromId;
    private Long toId;
    private Long sourceId;
    private Long destinationId;
}
