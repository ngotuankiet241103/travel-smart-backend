package com.travelsmart.trip_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class TripUpdateRequest {
    @NotEmpty(message = "TITLE_INVALID")
    private String title;
    @NotNull(message = "START_DATE_INVALID")
    private Date startDate;
    @NotNull(message = "END_DATE_INVALID")
    private Date endDate;
    @NotNull(message = "LOCATION_INVALID")
    private Long locationId;

}
