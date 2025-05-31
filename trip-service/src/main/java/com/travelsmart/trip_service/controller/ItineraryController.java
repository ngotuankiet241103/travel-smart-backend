package com.travelsmart.trip_service.controller;

import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.ApiResponse;
import com.travelsmart.trip_service.dto.response.DestinationResponse;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.service.ItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itineraries")
@RequiredArgsConstructor
public class ItineraryController {
    private final ItineraryService itineraryService;
    @Operation(summary = "Create new itinerary in trip",description = "Returns single itinerary")
    @PostMapping("/{trip-id}/trip")
    public ApiResponse<ItineraryResponse> createItinerary(@PathVariable("trip-id") Long tripId,
                                                          @RequestBody ItineraryCreateRequest itineraryCreateRequest) {
        return ApiResponse.<ItineraryResponse>builder()
                .result(itineraryService.createItinerary(tripId,itineraryCreateRequest))
                .build();
    }
    @Operation(summary = "Create new destination in itinerary",description = "Returns single itinerary")
    @PostMapping("/{id}")
    public ApiResponse<ItineraryResponse> create(@PathVariable("id") Long id,
                                                 @Valid @RequestBody ItineraryRequest itineraryRequest) {
        return ApiResponse.<ItineraryResponse>builder()
                .result(itineraryService.create(id,itineraryRequest))
                .build();
    }
    @Operation(summary = "Get itineraries by trip id",description = "Returns list itinerary")
    @GetMapping("/{trip-id}/trip")
    public ApiResponse<List<ItineraryResponse>> getByTripId(@PathVariable("trip-id") Long tripId){
        return ApiResponse.<List<ItineraryResponse>>builder()
                .result(itineraryService.findByTripId(tripId))
                .build();
    }
    @Operation(summary = "Swap destination in all itinerary",description = "Returns single itinerary")
    @PutMapping("/swap")
    public ApiResponse<ItineraryResponse> swapItinerary(@Valid @RequestBody ItinerarySwapRequest itinerarySwapRequest){
        return ApiResponse.<ItineraryResponse>builder()
                .result(itineraryService.swapItinerary(itinerarySwapRequest))
                .build();
    }
    @Operation(summary = "Optimize itineraries in trip",description = "Returns list itinerary")
    @PutMapping("/{id}")
    public ApiResponse<ItineraryResponse> updateItinerary(@PathVariable("id") Long id ,
                                                          @RequestBody ItineraryNoteUpdateRequest updateRequest){
        return ApiResponse.<ItineraryResponse>builder()
                .result(itineraryService.updateItinerary(id, updateRequest))
                .build();
    }
    @Operation(summary = "Replace destination",description = "Returns single response")
    @PutMapping("/destination/{destination-id}")
    public ApiResponse<DestinationResponse> replaceLocation(@PathVariable("destination-id") Long destinationId,
                                                            @RequestBody @Valid DestinationReplaceRequest destinationReplaceRequest){
        return ApiResponse.<DestinationResponse>builder()
                .result(itineraryService.replaceLocationInDestination(destinationId,destinationReplaceRequest))
                .build();
    }
    @Operation(summary = "Optimize itineraries in trip",description = "Returns list itinerary")
    @PutMapping("/optimize/{trip-id}")
    public ApiResponse<List<ItineraryResponse>> optimizeItinerary(@PathVariable("trip-id") Long tripId){
        return ApiResponse.<List<ItineraryResponse>>builder()
                .result(itineraryService.optimzeItinerariesInTrip(tripId))
                .build();
    }

    @Operation(summary = "Delete itinerary")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable("id") Long id,
                                        @Valid @RequestBody ItineraryDeleteRequest itineraryDeleteRequest){
        itineraryService.deleteById(id,itineraryDeleteRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @Operation(summary = "Delete destination in itinerary")
    @DeleteMapping("/{destination-id}/destination")
    public ApiResponse<Void> deleteDestinationById(@PathVariable("destination-id") Long id,
                                                   @Valid @RequestBody DestinationDeleteRequest destinationDeleteRequest){
        itineraryService.deleteDestinationById(id,destinationDeleteRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

}
