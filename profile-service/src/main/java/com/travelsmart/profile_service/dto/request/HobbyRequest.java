package com.travelsmart.profile_service.dto.request;

import com.travelsmart.profile_service.entity.HobbyType;
import com.travelsmart.profile_service.entity.LocationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class HobbyRequest {
    @NotNull(message = "HOBBY_INVALID")
    private List<HobbyType> hobbies;
}
