package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.response.Geojson;
import com.travelsmart.location_service.entity.Address;
import com.travelsmart.location_service.entity.LocationImageEntity;
import com.travelsmart.location_service.utils.StringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LocationRequest {
    @NotEmpty(message = "CATEGORY_INVALID")
    private String category;
    @NotEmpty(message = "DISPLAY_NAME_INVALID")
    private String display_name;
    @NotEmpty(message = "LON_INVALID")
    private String lon;
    @NotEmpty(message = "LAT_INVALID")
    private String lat;
    @NotEmpty(message = "NAME_INVALID")
    private String name;
    @NotEmpty(message = "TYPE_INVALID")
    private LocationType type;
    @NotEmpty(message = "ADDRESS_INVALID")
    private Address address;
    @NotNull(message = "IMAGE_INVALID")
    private Long imageId;
    @NotNull(message = "BOUNDINGBOX_INVALID")
    private List<String> boundingbox;

}
