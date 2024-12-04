package com.travelsmart.trip_service.dto.response.httpclient;

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
    private String userName;
    private List<String> hobbies;
}
