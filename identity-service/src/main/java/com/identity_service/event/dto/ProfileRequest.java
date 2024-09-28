package com.identity_service.event.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {
    private String id;
    private String firstName;
    private String lastName;

}
