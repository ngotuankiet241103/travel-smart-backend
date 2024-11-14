package com.travelsmart.trip_service.controller;

import com.travelsmart.trip_service.constant.TripPermission;
import com.travelsmart.trip_service.dto.request.TripGenerateRequest;
import com.travelsmart.trip_service.dto.request.TripRequest;
import com.travelsmart.trip_service.dto.request.TripShareRequest;
import com.travelsmart.trip_service.dto.request.TripUpdateRequest;
import com.travelsmart.trip_service.dto.response.*;
import com.travelsmart.trip_service.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;
    @Operation(summary = "Create new trip",description = "Returns single trip")
    @PostMapping
    public ApiResponse<TripResponse> create(@Valid @RequestBody TripRequest tripRequest){
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
    @Operation(summary = "Get all user in trip",description = "Returns list trip")
    @GetMapping("/users/{trip-id}/trip")
    public ApiResponse<List<UserTripResponse>> getAllUserInTrip(@PathVariable("trip-id") Long id){
        return ApiResponse.<List<UserTripResponse>>builder()
                .result(tripService.getAllUserInTrip(id))
                .build();
    }
    @PostMapping("/generate")
    public ApiResponse<TripGenerateResponse> generateTrip(@Valid @RequestBody TripGenerateRequest tripGenerateRequest){

        return ApiResponse.<TripGenerateResponse>builder()
                .result(tripService.generateTrip(tripGenerateRequest))
                .build();
    }
    @Operation(summary = "Update information trip",description = "Returns single trip")
    @PutMapping("/{id}")
    public ApiResponse<TripResponse> update(@PathVariable("id") Long id, @Valid @RequestBody TripUpdateRequest tripUpdateRequest){
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
    public ApiResponse<String> shareTrip(@PathVariable("id") Long id,@Valid @RequestBody TripShareRequest tripShareRequest){
        return  ApiResponse.<String>builder()
                .result(tripService.shareTrip(id,tripShareRequest))
                .build();
    }
    @Operation(summary = "Delete by trip id")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteByTripId(@PathVariable("id") Long id){

        return ApiResponse.<String>builder()
                .result(tripService.deleteById(id))
                .build();
    }


}
