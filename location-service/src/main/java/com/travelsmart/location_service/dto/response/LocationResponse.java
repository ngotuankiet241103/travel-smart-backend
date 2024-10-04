package com.travelsmart.location_service.dto.response;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.entity.Address;
import com.travelsmart.location_service.entity.LocationImageEntity;
import com.travelsmart.location_service.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class LocationResponse extends LocationTemplateResponse{
    private LocationType type;
    private LocationImageResponse thumbnail;
    private int timeVisit;
}
