package com.travelsmart.trip_service.dto.response;

import com.travelsmart.trip_service.constant.TripPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTripResponse {
    private String email;
    private String name;
    private String avatar;
    private TripPermission tripPermission;
}
