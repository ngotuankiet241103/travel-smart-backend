package com.travelsmart.event.dto;


import com.travelsmart.location_service.constant.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInRequest {
    private String userId;
    private LocationType type;
}
