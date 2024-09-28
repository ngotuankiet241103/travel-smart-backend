package com.travelsmart.trip_service.controller;

import com.travelsmart.trip_service.constant.TripPermission;
import com.travelsmart.trip_service.dto.request.TripRequest;
import com.travelsmart.trip_service.dto.request.TripShareRequest;
import com.travelsmart.trip_service.dto.request.TripUpdateRequest;
import com.travelsmart.trip_service.dto.response.ApiResponse;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.dto.response.TripResponse;
import com.travelsmart.trip_service.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;
    @Operation(summary = "Create new trip",description = "Returns single trip")
    @PostMapping
    public ApiResponse<TripResponse> create(@RequestBody TripRequest tripRequest){
        return  ApiResponse.<TripResponse>builder()
                .result(tripService.create(tripRequest))
                .build();
    }
    @Operation(summary = "Get trips of user",description = "Returns list trip")
    @GetMapping("/my-trip")
    public ApiResponse<List<TripResponse>> getMyTrip(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<TripResponse>>builder()
                .result(tripService.findMyTrip(authentication.getName()))
                .build();
    }
    @Operation(summary = "Update information trip",description = "Returns single trip")
    @PutMapping("/{id}")
    public ApiResponse<TripResponse> update(@PathVariable("id") Long id, @RequestBody TripUpdateRequest tripUpdateRequest){
        return  ApiResponse.<TripResponse>builder()
                .result(tripService.update(id,tripUpdateRequest))
                .build();
    }
    @Operation(summary = "Get all permissions of trip",description = "Returns list permission of trip")
    @GetMapping("/permissions")
    public ApiResponse<List<TripPermission>> getTripPermissions() {
        return ApiResponse.<List<TripPermission>>builder()
                .result(TripPermission.getPermssions())
                .build();
    }
    @Operation(summary = "Share trip with other user",description = "Returns notification")
    @PostMapping("/share/{id}")
    public ApiResponse<String> shareTrip(@PathVariable("id") Long id, @RequestBody TripShareRequest tripShareRequest){
        return  ApiResponse.<String>builder()
                .result(tripService.shareTrip(id,tripShareRequest))
                .build();
    }


}
