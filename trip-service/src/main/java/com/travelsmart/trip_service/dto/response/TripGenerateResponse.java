package com.travelsmart.trip_service.dto.response;

import com.travelsmart.trip_service.constant.TripPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripGenerateResponse {
    private Long id;
    private String title;
    private String code;
    private Date startDate;
    private Date endDate;
    private String image;
    private TripPermission permission;
    private List<ItineraryResponse> itineraries;
}
