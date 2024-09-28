package com.travelsmart.trip_service.mapper;

import com.travelsmart.trip_service.dto.request.TripRequest;
import com.travelsmart.trip_service.dto.request.TripUpdateRequest;
import com.travelsmart.trip_service.dto.response.TripResponse;
import com.travelsmart.trip_service.entity.TripEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TripMapper {
    TripEntity toTripEntity(TripRequest tripRequest);
    TripEntity toTripEntity(TripUpdateRequest tripUpdateRequest);
    TripResponse toTripResponse(TripEntity trip);
}
