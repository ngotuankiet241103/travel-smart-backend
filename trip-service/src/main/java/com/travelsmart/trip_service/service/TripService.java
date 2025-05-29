package com.travelsmart.trip_service.service;

import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.*;

import java.util.Date;
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

    PageableResponse<List<TripResponse>> getAll(Date from, Date to, int page, int limit);
}
