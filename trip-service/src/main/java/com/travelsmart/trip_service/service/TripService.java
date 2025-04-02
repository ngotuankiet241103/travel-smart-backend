package com.travelsmart.trip_service.service;

import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.TripGenerateResponse;
import com.travelsmart.trip_service.dto.response.TripReport;
import com.travelsmart.trip_service.dto.response.TripResponse;
import com.travelsmart.trip_service.dto.response.UserTripResponse;

import java.util.List;
import java.util.Map;

public interface TripService {
    TripResponse create(TripRequest tripRequest);

    List<TripResponse> findMyTrip(String name);

    TripResponse update(Long id,TripUpdateRequest tripUpdateRequest);

    String shareTrip(Long id, TripShareRequest tripShareRequest);

    TripGenerateResponse generateTrip(TripGenerateRequest tripGenerateRequest);

    List<UserTripResponse> getAllUserInTrip(Long id);

    String deleteById(Long id);

    TripReport getReport(ReportType type,Integer year,Integer month);

    Map<String, Object> getStatistics();
}
