package com.travelsmart.saga.profile.command;

import lombok.Builder;
import lombok.Data;

@Builder
public record ProfileCommand(String userId,String email,String firstName,String lastName ) {
}
