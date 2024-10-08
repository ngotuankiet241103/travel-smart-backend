package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.response.Geojson;
import com.travelsmart.location_service.entity.Address;
import com.travelsmart.location_service.entity.LocationImageEntity;
import com.travelsmart.location_service.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LocationRequest {

    private String category;
    private String display_name;
    private String lon;
    private String lat;
    private String name;
    private LocationType type;
    private Address address;
    private Long imageId;
    private List<String> boundingbox;

}
