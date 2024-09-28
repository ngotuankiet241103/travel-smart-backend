package com.travelsmart.trip_service.dto.request;

import lombok.Data;

import java.util.Date;
@Data
public class TripRequest {
    private String title;
    private Date startDate;
    private Date endDate;
    private Long locationId;
}
