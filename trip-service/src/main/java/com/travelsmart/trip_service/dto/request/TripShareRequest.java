package com.travelsmart.trip_service.dto.request;

import com.travelsmart.trip_service.constant.TripPermission;
import lombok.Data;

@Data
public class TripShareRequest {
    private String email;
    private TripPermission tripPermission;
}
