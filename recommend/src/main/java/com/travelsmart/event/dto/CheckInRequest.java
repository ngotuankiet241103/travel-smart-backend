package com.travelsmart.event.dto;

import com.travelsmart.recommend.constrant.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckInRequest {
    private String userId;
    private LocationType type;
}
