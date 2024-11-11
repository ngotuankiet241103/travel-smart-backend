package com.travelsmart.trip_service.service;

import com.travelsmart.trip_service.dto.request.TripGenerateRequest;
import com.travelsmart.trip_service.dto.request.TripRequest;
import com.travelsmart.trip_service.dto.request.TripShareRequest;
import com.travelsmart.trip_service.dto.request.TripUpdateRequest;
import com.travelsmart.trip_service.dto.response.TripGenerateResponse;
import com.travelsmart.trip_service.dto.response.TripResponse;
import com.travelsmart.trip_service.dto.response.UserTripResponse;

import java.util.List;

public interface TripService {
    TripResponse create(TripRequest tripRequest);

    List<TripResponse> findMyTrip(String name);

    TripResponse update(Long id,TripUpdateRequest tripUpdateRequest);

    String shareTrip(Long id, TripShareRequest tripShareRequest);

    TripGenerateResponse generateTrip(TripGenerateRequest tripGenerateRequest);

    List<UserTripResponse> getAllUserInTrip(Long id);

    String deleteById(Long id);
}
