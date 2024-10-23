package com.travelsmart.trip_service.dto.request;

import com.travelsmart.trip_service.constant.LocationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class TripGenerateRequest extends TripRequest {
    @NotNull(message = "TYPES_INVALID")
    private List<LocationType> type;
}
