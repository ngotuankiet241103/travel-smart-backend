package com.travelsmart.profile_service.dto.request;

import com.travelsmart.profile_service.entity.LocationType;
import lombok.Data;

import java.util.List;
@Data
public class HobbyRequest {
    private List<LocationType> hobbies;
}