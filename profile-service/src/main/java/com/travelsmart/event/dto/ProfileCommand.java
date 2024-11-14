package com.travelsmart.event.dto;

import lombok.Builder;

@Builder
public record ProfileCommand(String userId, String email, String firstName, String lastName ) {
}
