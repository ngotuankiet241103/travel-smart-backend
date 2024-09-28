package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.entity.Address;
import com.travelsmart.location_service.utils.StringListConverter;
import jakarta.persistence.Convert;
import lombok.Data;

import java.util.List;
@Data
public class LocationUpdateRequest {
    private String category;
    private String display_name;
    private String lon;
    private String lat;
    private String name;
    private String type;
    private Address address;
    private Long imageId;
    private List<Long> collectionIds;

    private List<String> boundingbox;
}
