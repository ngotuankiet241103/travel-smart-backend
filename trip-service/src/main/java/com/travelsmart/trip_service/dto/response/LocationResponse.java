package com.travelsmart.trip_service.dto.response;

import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.location_service.entity.Address;
import com.travelsmart.trip_service.constant.LocationType;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LocationResponse {
    private Long place_id;
    private String category;
    private String display_name;
    private String lon;
    private String lat;
    private String name;
    private LocationType type;
    private Address address;
    private LocationImageResponse thumbnail;
    private List<String> boundingbox;
    private int timeVisit;
}