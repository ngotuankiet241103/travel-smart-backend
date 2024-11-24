package com.travelsmart.profile_service.dto.response;

import com.travelsmart.profile_service.entity.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private List<LocationType> hobbies;
}
