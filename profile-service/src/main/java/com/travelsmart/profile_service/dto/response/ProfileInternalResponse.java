package com.travelsmart.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileInternalResponse {
    private String id;
    private String userName;
    private String email;
    private String avatar;
    private List<String> hobbies;
}
