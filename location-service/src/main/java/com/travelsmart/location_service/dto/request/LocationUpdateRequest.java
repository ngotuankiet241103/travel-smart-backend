package com.travelsmart.location_service.dto.request;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.entity.Address;
import com.travelsmart.location_service.utils.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class LocationUpdateRequest {
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
    @NotNull(message = "TYPE_INVALID")
    private LocationType type;
    @NotNull(message = "ADDRESS_INVALID")
    private Address address;
    @NotNull(message = "IMAGE_INVALID")
    private Long imageId;
    @NotNull(message = "BOUNDINGBOX_INVALID")
    private List<String> boundingbox;
}
