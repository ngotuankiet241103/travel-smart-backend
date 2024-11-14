package com.travelsmart.location_service.controller;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.request.*;
import com.travelsmart.location_service.dto.response.*;
import com.travelsmart.location_service.service.LocationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @Operation(summary = "Get locations by search param through third part api",description = "Returns list location")
    @GetMapping("/search")
    public ApiResponse<List<LocationTemplateResponse>> getBySearchAndLimit(@RequestParam("q") String search, @RequestParam(value = "limit", defaultValue = "1") int limit){
        return ApiResponse.<List<LocationTemplateResponse>>builder()
                .result(locationService.findBySearch(search,limit))
                .build();
    }
    @Operation(summary = "Get location by coordinates",description = "Returns single location")
    @GetMapping("/search/reverse")
    public ApiResponse<LocationTemplateResponse> getByCoordinates(@RequestParam("lon") String lon, @RequestParam("lat") String lat ){

        return ApiResponse.<LocationTemplateResponse>builder()
                .result(locationService.findByCoordinates(lon,lat))
                .build();
    }
    @Operation(summary = "Get locations by param search",description = "Returns all location")
    @GetMapping
    public ApiResponse<List<LocationResponse>> getBySearch(@RequestParam("q") String search,
                                                           @RequestParam(value = "type",defaultValue = "TOURIST_ATTRACTION") LocationType locationType,
                                                           @RequestParam(value = "limit", defaultValue = "4") int limit){
        Pageable pageable = PageRequest.of(0,limit);
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findBySearchLocal(locationType,search,pageable))
                .build();
    }
    @Operation(summary = "Get location by coordinates",description = "Returns single location")
    @GetMapping("/lookup")
    public ApiResponse<LocationResponse> getByCoordinatesLocal(@RequestParam("lon") String lon, @RequestParam("lat") String lat ){

        return ApiResponse.<LocationResponse>builder()
                .result(locationService.findByCoordinatesLocal(lon,lat))
                .build();
    }

    @Operation(summary = "Get all location",description = "Returns list location")
    @GetMapping("/all")
    public ApiResponse<PageableResponse<List<LocationResponse>>> getAll(@RequestParam(value = "limit",defaultValue = "5") int limit,
                                                                        @RequestParam(value = "page",defaultValue = "1") int page){
        Pageable pageable = PageRequest.of(page - 1,limit);
        return ApiResponse.<PageableResponse<List<LocationResponse>>>builder()
                .result(locationService.findAll(pageable))
                .build();
    }
    @Operation(summary = "Get newest location",description = "Returns list location")
    @GetMapping("/news")
    public ApiResponse<List<LocationResponse>> getNewestLocation(@RequestParam(value = "limit",defaultValue = "5") int limit){
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findNewest(limit))
                .build();
    }
    @Operation(summary = "Create new location by coordinates",description = "Returns single location")
    @PostMapping("/coordinates")
    public ApiResponse<LocationResponse> createByCoordinates(@Valid @RequestBody LocationCoordinateRequest locationCoordinateRequest){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.createByCoordinates(locationCoordinateRequest))
                .build();
    }


    @Operation(summary = "Create new location",description = "Returns single location")
    @PostMapping
    public ApiResponse<LocationResponse> create(@Valid @RequestBody LocationRequest locationRequest) throws ParseException {
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.create(locationRequest))
                .build();
    }
    @Operation(summary = "Create new location image",description = "Returns single location image")
    @PostMapping("/image")
    public ApiResponse<LocationImageResponse> createImageLocation(@Valid @ModelAttribute ImageCreateRequest imageCreateRequest){
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
    public ApiResponse<LocationResponse> createImageLocation(@PathVariable("place-id") Long id,@Valid @RequestBody LocationUpdateRequest locationUpdateRequest){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.update(id,locationUpdateRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update status location",description = "Returns single location")
    @PutMapping("/status/{place-id}")
    public ApiResponse<LocationResponse> updateLocationStatus(@PathVariable("place-id") Long id,@Valid @RequestBody LocationStatusRequest locationStatusRequest){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.updateStatus(id, locationStatusRequest))
                .build();
    }
    @Operation(summary = "Get locations by type",description = "Returns list location")
    @GetMapping("/location-type/{locationType}")
    public ApiResponse<List<LocationResponse>> getLocationByType(@PathVariable("locationType") LocationType locationType){
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findAllByType(locationType))
                .build();
    }
    @GetMapping("/type")
    public ApiResponse<List<LocationType>> getLocationTypes(){
        return ApiResponse.<List<LocationType>>builder()
                .result(LocationType.getLocationTypes())
                .build();
    }
    @GetMapping("/radius")
    public ApiResponse<List<LocationResponse>> getLocationInRadius(@RequestParam("lon") String lon,
                                                                   @RequestParam("lat") String lat,
                                                                   @RequestParam("radius") double radius,
                                                                   @RequestParam(value = "q",defaultValue = "") String search,
                                                                   @RequestParam(value = "type",defaultValue = "ADMINISTRATIVE") LocationType type){
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findByRadius(lon,lat,radius,search,type))
                .build();
    }

    @GetMapping("/internal/{id}")
    public ApiResponse<LocationResponse> getById(@PathVariable("id") Long id){
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.findById(id))
                .build();
    }
    @Hidden
    @GetMapping("/internal/type")
    public ApiResponse<List<LocationResponse>> getById(@RequestParam("types") List<LocationType> types,@RequestParam("locationId") Long id){
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.findByType(id,types))
                .build();
    }



}
