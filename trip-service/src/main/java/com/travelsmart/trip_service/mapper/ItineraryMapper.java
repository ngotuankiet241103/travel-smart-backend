package com.travelsmart.trip_service.mapper;

import com.travelsmart.trip_service.dto.request.ItineraryCreateRequest;
import com.travelsmart.trip_service.entity.ItineraryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItineraryMapper {
    ItineraryEntity toItineraryEntity(ItineraryCreateRequest itineraryCreateRequest);
}
