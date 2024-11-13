package com.travelsmart.trip_service.service;

import com.travelsmart.trip_service.constant.LocationType;
import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.DestinationResponse;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.entity.TripEntity;

import java.util.List;

public interface ItineraryService {
    void creatItinerariesByTrip(TripEntity trip);

    void updateItineraries(TripEntity trip);

    ItineraryResponse createItinerary(Long tripId,ItineraryCreateRequest itineraryCreateRequest);

    ItineraryResponse create(Long id, ItineraryRequest itineraryRequest);

    List<ItineraryResponse> findByTripId(Long tripId);

    ItineraryResponse swapItinerary(ItinerarySwapRequest itinerarySwapRequest);

    void deleteById(Long id, ItineraryDeleteRequest itineraryDeleteRequest);

    void deleteDestinationById(Long id,DestinationDeleteRequest destinationDeleteRequest);

    List<ItineraryResponse> generateTrip(TripEntity trip, List<LocationType> types);

    List<ItineraryResponse> optimzeItinerariesInTrip(Long tripId);

    void deleteByTripId(Long id);

    ItineraryResponse updateItinerary(Long id, ItineraryNoteUpdateRequest updateRequest);

    DestinationResponse replaceLocationInDestination(Long destinationId, DestinationReplaceRequest destinationReplaceRequest);
}
