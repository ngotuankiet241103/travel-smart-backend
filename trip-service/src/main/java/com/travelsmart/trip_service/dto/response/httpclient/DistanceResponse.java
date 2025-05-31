package com.travelsmart.trip_service.dto.response.httpclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistanceResponse {
    private double distance = 0;
    private double time = 0;
    private double timeVisit = 0;

}
