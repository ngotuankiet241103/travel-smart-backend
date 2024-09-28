package com.travelsmart.location_service.controller;

import com.travelsmart.location_service.dto.request.ImageCreateRequest;
import com.travelsmart.location_service.dto.request.LocationRequest;
import com.travelsmart.location_service.dto.request.LocationUpdateRequest;
import com.travelsmart.location_service.dto.response.ApiResponse;
import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.location_service.dto.response.LocationResponse;
import com.travelsmart.location_service.service.LocationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @Operation(summary = "Get locations by search param through third part api",description = "Returns list location")
    @GetMapping("/search")
    public ApiResponse<List<LocationResponse>> getBySearchAndLimit(@RequestParam("q") String search, @RequestParam(value = "limit", defaultValue = "1") int limit){
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findBySearch(search,limit))
                .build();
    }
    @Operation(summary = "Get location by coordinates",description = "Returns single location")
    @GetMapping("/search/reverse")
    public ApiResponse<LocationResponse> getByCoordinates(@RequestParam("lon") String lon, @RequestParam("lat") String lat ){

        return ApiResponse.<LocationResponse>builder()
                .result(locationService.findByCoordinates(lon,lat))
                .build();
    }
    @Operation(summary = "Get locations by param search",description = "Returns all location")
    @GetMapping
    public ApiResponse<List<LocationResponse>> getBySearch(@RequestParam("q") String search, @RequestParam(value = "limit", defaultValue = "1") int limit){
        Pageable pageable = PageRequest.of(0,limit);
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findBySearchLocal(search,pageable))
                .build();
    }
    @Operation(summary = "Get location by coordinates",description = "Returns single location")
    @GetMapping("/lookup")
    public ApiResponse<LocationResponse> getByCoordinatesLocal(@RequestParam("lon") String lon, @RequestParam("lat") String lat ){

        return ApiResponse.<LocationResponse>builder()
                .result(locationService.findByCoordinatesLocal(lon,lat))
                .build();
    }
    @Operation(summary = "Create new location",description = "Returns single location")
    @PostMapping
    public ApiResponse<LocationResponse> create(@RequestBody LocationRequest locationRequest){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.create(locationRequest))
                .build();
    }
    @Operation(summary = "Create new location image",description = "Returns single location image")
    @PostMapping("/image")
    public ApiResponse<LocationImageResponse> createImageLocation(@ModelAttribute ImageCreateRequest imageCreateRequest){
        return ApiResponse.<LocationImageResponse>builder()
                .result(locationService.createImageLocation(imageCreateRequest))
                .build();
    }
    @Operation(summary = "Delete location image by id")
    @DeleteMapping("/image/{id}")
    public ApiResponse<Void> deleteImageLocation(@PathVariable("id") Long id){
        locationService.deleteImageLocation(id);
        return ApiResponse.<Void>builder()
                .build();
    }
    @Operation(summary = "Update information location",description = "Returns single location image")
    @PutMapping("/{place-id}")
    public ApiResponse<LocationResponse> createImageLocation(@PathVariable("place-id") Long id, @RequestBody LocationUpdateRequest locationUpdateRequest){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.update(id,locationUpdateRequest))
                .build();
    }
    @Hidden
    @GetMapping("/internal/{id}")
    public ApiResponse<LocationResponse> getById(@PathVariable("id") Long id){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.findById(id))
                .build();
    }

}
