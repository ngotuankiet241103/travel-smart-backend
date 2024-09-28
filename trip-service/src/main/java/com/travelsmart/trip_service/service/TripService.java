package com.travelsmart.trip_service.service;

import com.travelsmart.trip_service.dto.request.TripRequest;
import com.travelsmart.trip_service.dto.request.TripShareRequest;
import com.travelsmart.trip_service.dto.request.TripUpdateRequest;
import com.travelsmart.trip_service.dto.response.TripResponse;

import java.util.List;

public interface TripService {
    TripResponse create(TripRequest tripRequest);

    List<TripResponse> findMyTrip(String name);

    TripResponse update(Long id,TripUpdateRequest tripUpdateRequest);

    String shareTrip(Long id, TripShareRequest tripShareRequest);
}
