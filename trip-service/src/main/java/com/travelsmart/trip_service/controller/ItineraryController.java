package com.travelsmart.trip_service.controller;

import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.ApiResponse;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.service.ItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/itineraries")
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
                                                          @RequestBody ItineraryRequest itineraryRequest) {
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
    public ApiResponse<ItineraryResponse> swapItinerary(@RequestBody ItinerarySwapRequest itinerarySwapRequest){
        return ApiResponse.<ItineraryResponse>builder()
                .result(itineraryService.swapItinerary(itinerarySwapRequest))
                .build();
    }
    @Operation(summary = "Delete itinerary")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable("id") Long id, @RequestBody ItineraryDeleteRequest itineraryDeleteRequest){
        itineraryService.deleteById(id,itineraryDeleteRequest);
        return ApiResponse.<Void>builder()
                .build();
    }
    @Operation(summary = "Delete destination in itinerary")
    @DeleteMapping("/{destination-id}/destination")
    public ApiResponse<Void> deleteDestinationById(@PathVariable("destination-id") Long id,
                                                   @RequestBody DestinationDeleteRequest destinationDeleteRequest){
        itineraryService.deleteDestinationById(id,destinationDeleteRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

}
