package com.travelsmart.location_service.mapper;

import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.location_service.entity.LocationImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationImageMapper {
    LocationImageResponse toLocationImageResponse(LocationImageEntity locationImageEntity);
}
