package com.travelsmart.location_service.dto.response;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.entity.Address;
import lombok.Data;

import java.util.List;
@Data
public class LocationTemplateResponse {
    private Long place_id;
    private String category;
    private String display_name;
    private String lon;
    private String lat;
    private String name;
    private Address address;
    private LocationImageResponse thumbnail;
    private List<String> boundingbox;
    private Double starRate;
    private int timeVisit;
}
