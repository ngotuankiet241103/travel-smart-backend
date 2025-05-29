package com.travelsmart.review_service.dto.response.httpclient;

import com.travelsmart.review_service.constant.LocationType;

import lombok.Data;

import java.util.List;

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