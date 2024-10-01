package com.travelsmart.location_service.controller;

import com.travelsmart.location_service.dto.request.IntroduceRequest;
import com.travelsmart.location_service.dto.request.IntroduceUpdateRequest;
import com.travelsmart.location_service.dto.request.LocationUpdateRequest;
import com.travelsmart.location_service.dto.response.ApiResponse;
import com.travelsmart.location_service.dto.response.IntroduceResponse;
import com.travelsmart.location_service.dto.response.LocationResponse;
import com.travelsmart.location_service.service.IntroduceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/introduces")
@RequiredArgsConstructor
public class IntroduceController {
    private final IntroduceService introduceService;
    @Operation(summary = "Create new introduce for location",description = "Returns single introduce")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<IntroduceResponse> createIntroduce(@RequestBody IntroduceRequest introduceRequest){
        return ApiResponse.<IntroduceResponse>builder()
                .result(introduceService.create(introduceRequest))
                .build();
    }
    @Operation(summary = "Get introduce by location id",description = "Returns single introduce")
    @GetMapping("/{location-id}/location")
    public ApiResponse<IntroduceResponse> getByLocationId(@PathVariable("location-id") Long locationId){
        return ApiResponse.<IntroduceResponse>builder()
                .result(introduceService.getByLocationId(locationId))
                .build();
    }
    @Operation(summary = "Update introduce",description = "Returns single introduce")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<IntroduceResponse> update(@PathVariable("id") Long locationId,
                                                 @RequestBody IntroduceUpdateRequest updateRequest){
        return ApiResponse.<IntroduceResponse>builder()
                .result(introduceService.update(locationId,updateRequest))
                .build();
    }

}
