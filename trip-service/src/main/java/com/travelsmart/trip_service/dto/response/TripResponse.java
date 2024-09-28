package com.travelsmart.trip_service.dto.response;

import com.travelsmart.trip_service.constant.TripPermission;
import lombok.Data;

import java.util.Date;
@Data
public class TripResponse {
    private Long id;
    private String title;
    private String code;
    private Date startDate;
    private Date endDate;
    private String image;
    private TripPermission permission;
}
