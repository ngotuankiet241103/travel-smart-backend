package com.travelsmart.trip_service.dto.request;

import lombok.Data;

import java.util.Date;
@Data
public class TripUpdateRequest {
    private String title;
    private Date startDate;
    private Date endDate;
    private Long locationId;

}
