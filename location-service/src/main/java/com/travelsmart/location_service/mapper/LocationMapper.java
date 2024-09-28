package com.travelsmart.location_service.mapper;

import com.travelsmart.location_service.dto.request.LocationRequest;
import com.travelsmart.location_service.dto.request.LocationUpdateRequest;
import com.travelsmart.location_service.dto.response.LocationResponse;
import com.travelsmart.location_service.entity.LocationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationEntity toLocationEntity(LocationRequest locationRequest);
    LocationEntity toLocationEntity(LocationUpdateRequest locationRequest);
    LocationResponse toLocationResponse(LocationEntity locationEntity);
}
